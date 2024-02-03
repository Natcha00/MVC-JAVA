import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;

public class ReservationView extends JFrame {

    private JTextField userText0;
    private JTextField userText;
    private List<JCheckBox> checkBoxList = new ArrayList<>();
    private ReservationController controller = new ReservationController();
    JFrame frame = new JFrame("Reservation");
    JPanel panel = new JPanel();
    JFrame frame2 = new JFrame("History");
    JPanel panel2 = new JPanel();

    public static void main(String[] args) {
        ReservationView reservationView = new ReservationView();

    }

    public ReservationView() {
        initialize();
    }

    private void initialize() {
        frame.setSize(400, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.setLayout(null);
        frame.add(panel);

        initializeContent(frame, panel);

        frame.setVisible(true);
    }

    private void initialize2(String userName) {
        frame2.setSize(400, 420);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel2.setLayout(null);
        frame2.add(panel2);

        JLabel userLabel = new JLabel("UserName : " + userName);
        userLabel.setBounds(20, 40, 200, 25);
        panel2.add(userLabel);

        User user = controller.getUserInfo(userName);
        ArrayList<Room> roomList = user.getReserveRoom();

        int i = 0;
        for (Room r : roomList) {
            ArrayList<Game> gameList = r.getGame();
            String gameListString = "";
            for (Game g : gameList) {
                gameListString += g.getName() + ",";
            }

            JLabel roomLabel = new JLabel("Room No : " + r.getName());
            roomLabel.setBounds(20, 60 + 20 * i, 300, 25);
            panel2.add(roomLabel);

            JLabel gameLabel = new JLabel("Game : " + gameListString);
            gameLabel.setBounds(20, 80 + 20 * i, 300, 25);
            panel2.add(gameLabel);
            i++;
        }

        frame2.setVisible(true);
    }

    private void initializeContent(JFrame frame, JPanel panel) {
        // User Name field
        JLabel userNameLabel = new JLabel("UserName");
        userNameLabel.setBounds(20, 40, 80, 25);
        panel.add(userNameLabel);

        userText0 = new JTextField(20);
        userText0.setBounds(100, 40, 165, 25);
        panel.add(userText0);

        // Room No. field
        JLabel roomNoLabel = new JLabel("Room No.");
        roomNoLabel.setBounds(20, 80, 80, 25);
        panel.add(roomNoLabel);

        // Room available
        ArrayList<Room> rooms = controller.getRooms();
        String roomsAvailableString = "";
        for (Room r : rooms) {
            roomsAvailableString += r.getName() + ",";
        }
        JLabel roomAvailable = new JLabel("หมายเลขห้องที่จองได้ : " + roomsAvailableString);
        roomAvailable.setBounds(20, 100, 200, 25);
        panel.add(roomAvailable);

        panel.add(userNameLabel);
        userText = new JTextField(20);
        userText.setBounds(100, 80, 165, 25);
        panel.add(userText);

        // Checkboxes

        ArrayList<Game> games = controller.getGames();

        for (int i = 0; i < games.size(); i++) {
            addCheckBox(games.get(i).getName() + "(เหลืออยู่ : " + games.get(i).getAvailable() + " กล่อง)", 20,
                    120 + 20 * i, panel);
        }

        // Commit button
        JButton commitButton = new JButton("COMMIT");
        commitButton.setBounds(100, 325, 100, 25);
        panel.add(commitButton);
        addCommitButtonListener(commitButton, new CommitButtonListener());

    }

    private void addCheckBox(String label, int x, int y, JPanel panel) {
        JCheckBox checkBox = new JCheckBox(label);
        checkBox.setBounds(x, y, 200, 23);
        panel.add(checkBox);
        // Add the checkbox to the list
        checkBoxList.add(checkBox);
    }

    public String getUserName() {
        return userText0.getText();
    }

    public String getRoomNumber() {
        return userText.getText();
    }

    private void addCommitButtonListener(JButton commitButton, ActionListener listener) {
        // Add action listener to the commit button
        commitButton.addActionListener(listener);
    }

    private class CommitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // This method will be called when the commit button is clicked
            handleCommitButtonClick();
        }
    }

    private void handleCommitButtonClick() {
        // You can access the input values here
        String userName = getUserName();
        String roomNumber = getRoomNumber();
        ArrayList<Game> gameSelected = new ArrayList<>();
        ArrayList<Game> games = controller.getGames();

        for (int i = 0; i < checkBoxList.size(); i++) {
            if (checkBoxList.get(i).isSelected()) {
                gameSelected.add(games.get(i));
            }
        }

        HashMap<String, String> result = controller.reserveRoom(userName, roomNumber, gameSelected);

        System.out.println(result);
        // Example: Show a message with the input values
        JOptionPane.showMessageDialog(null, result.get("message"));
        // Additional logic for handling checkboxes can be added here

        if (result.get("success") == "true") {
            frame.setVisible(false);
            initialize2(userName);
        }
    }

}

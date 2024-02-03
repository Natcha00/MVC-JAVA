import java.util.HashMap;
import java.util.ArrayList;

public class ReservationController {
    private ReservationModel model = new ReservationModel();

    /* กดตรวจสอบห้องว่าง */
    public HashMap<String, String> checkReserveRoom(String name) {
        HashMap<String, String> result = new HashMap<String, String>();
        /* ค้นหาชื่อห้องนี้จาก Model */
        Room room = model.findRoom(name);
        /*
         * ถ้าเจอห้องให้ เช็คต่อ ถ้าไม่เจอ ให้คืน success: false และ message: ไม่เจอห้อง
         */
        if (room == null) {
            result.put("success", "false");
            result.put("message", "ไม่เจอห้อง");
            return result;
        }

        /* ถ้าห้องที่เจอมีคนจองแล้ว ให้คืน success: false และ message: มีคนจองแล้ว */
        if (room.canReservedRoom()) {
            result.put("success", "false");
            result.put("message", "มีคนจองแล้ว");
        } else {
            /* ถ้าห้องที่เจอว่าง ให้คืน success: true และ message: ว่าง */
            result.put("success", "true");
            result.put("message", "ว่าง");
        }

        return result;
    }

    /* ดึงข้อมูลห้องไปแสดงใน View */
    public ArrayList<Room> getRooms() {
        return model.getRooms();
    }

    /* ดึงข้อมูลเกมไปแสดงใน View */
    public ArrayList<Game> getGames() {
        return model.getGames();
    }

    /* จองห้อง */
    public HashMap<String, String> reserveRoom(String userNameReserve, String roomName, ArrayList<Game> game) {

        HashMap<String, String> result = new HashMap<String, String>();
        /* ค้นหาชื่อห้อง */
        Room room = model.findRoom(roomName);

        /* ตรวจสอบว่าห้องมีอยู่จริงหรือไม่ */
        if (room == null) {
            result.put("success", "false");
            result.put("message", "ไม่พบชื่อห้องนี้");
            return result;
        }

        /* ตรวจสอบว่าห้องยังว่างอยู่หรือไม่ */
        if (!room.canReservedRoom()) {
            result.put("success", "false");
            result.put("message", "มีคนจองแล้ว");
            return result;
        }

        /* ตรวจสอบว่า user เลือกเกมขั้นต่ำมา 2 เกม หรือไม่ */
        if (game.size() < 2) {
            result.put("success", "false");
            result.put("message", "กรุณาเลือกเกม ขั้นต่ำ 2เกม");
            return result;
        }

        /* ตรวจสอบว่าเกมที่เลือกมายัง available อยู่หรือไม่ */
        for (Game g : game) {
            if (!g.isAvailable()) {
                result.put("success", "false");
                result.put("message", "เกม " + g.getName() + " ไม่พร้อมบริการ");
                return result;
            }
        }

        /* ถ้าเงื่อนไขด้านบนไม่ติดอะไรก็ให้จองได้ */

        /* ค้นหาว่า user เคยจองเข้ามาหรือยัง ถ้าเจอเอา user นั้นมาใช้ ถ้าไม่สร้างใหม่ */
        User user = model.findUser(userNameReserve);
        if (user == null) {
            user = new User(userNameReserve);
            model.addNewUser(user);
        }

        model.userReserveRoom(user, room, game);

        result.put("success", "true");
        result.put("message", user.getName() + " จองห้อง " + room.getName() + " สำเร็จ");

        return result;
    }

    /* ดึงข้อมูลประวัติการจองไปแสดงใน view */
    public User getUserInfo(String userNameReserve) {
        return model.findUser(userNameReserve);
    }

    /* ยกเลิกการจองทั้งหมด */
    public HashMap<String, String> cancelAllReserved(User user) {
        HashMap<String, String> result = new HashMap<String, String>();
        model.userCancelAllReserve(user);
        result.put("success", "true");
        result.put("message", "ยกเลิกการจองทั้งหมดสำเร็จ");
        return result;
    }
}

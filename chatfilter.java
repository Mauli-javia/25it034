public class chatfilter {

    public static boolean hasKeyword(String message, String keyword) {
        if (message == null || keyword == null) return false;

        return message.toLowerCase().contains(keyword.toLowerCase());
    }
}
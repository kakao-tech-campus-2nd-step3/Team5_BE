package ojosama.talkak.video.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdExtractor {
    private static final Pattern pattern = Pattern.compile("(?:v=|\\/)([a-zA-Z0-9_-]{11})", Pattern.CASE_INSENSITIVE);

    public static String extract(String url) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

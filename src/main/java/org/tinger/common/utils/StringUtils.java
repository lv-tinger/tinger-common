package org.tinger.common.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tinger on 2022-10-01
 */
public class StringUtils {
    public static final String SPACE = " ";
    public static final String EMPTY = "";

    private static final String BRACKET = "[]";

    public static final int INDEX_NOT_FOUND = -1;

    public static boolean equals(CharSequence str1, CharSequence str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if (str1 == null || str2 == null) {
            return false;
        }

        if (str1.length() != str2.length()) {
            return false;
        }


        if (str1 == str2) {
            return true;
        }
        int length = str1.length();

        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean prefixWith(String str, String fix) {
        if (str == null || fix == null) {
            return false;
        }

        return str.startsWith(fix);
    }

    public static boolean suffixWith(String str, String fix) {
        if (str == null || fix == null) {
            return false;
        }

        return str.endsWith(fix);
    }

    public static boolean isEmpty(final CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneEmpty(final CharSequence... css) {
        return !isAnyEmpty(css);
    }

    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        final int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String trim(final String string) {
        return string == null ? null : string.trim();
    }

    public static String trimToEmpty(final String string) {
        return string == null ? EMPTY : string.trim();
    }

    public static String trimToNull(final String string) {
        final String trimString = trim(string);
        return isEmpty(trimString) ? null : trimString;
    }

    public static int indexOf(final String string, final String search) {
        if (string == null || search == null) {
            return INDEX_NOT_FOUND;
        }
        return string.indexOf(search);
    }

    public static int indexOfAny(final String string, final String... searches) {
        if (null == string || searches == null || searches.length == 0) {
            return INDEX_NOT_FOUND;
        }

        int index = Integer.MAX_VALUE;

        for (String search : searches) {
            int temp = indexOf(string, search);
            if (temp == -1) {
                continue;
            }

            if (temp < index) {
                index = temp;
            }
        }

        return index == Integer.MAX_VALUE ? INDEX_NOT_FOUND : index;

    }

    public static String firstUppercase(final String string) {
        if (string == null) {
            return null;
        }

        char[] chars = string.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }

    public static String firstLowercase(final String string) {
        if (string == null) {
            return null;
        }

        char[] chars = string.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }

    public static String uppercase(final String string) {
        return string == null ? null : string.toUpperCase();
    }

    public static String lowercase(final String string) {
        return string == null ? null : string.toLowerCase();
    }

    public static String substring(final String string, int start) {
        if (string == null) {
            return null;
        }

        if (start < 0) {
            start = string.length() + start;
        }

        if (start < 0) {
            start = 0;
        }

        if (start > string.length()) {
            return EMPTY;
        }

        return string.substring(start);
    }

    public static String substring(final String string, int start, int end) {
        if (string == null) {
            return null;
        }

        if (start < 0) {
            start = string.length() + start;
        }

        if (end < 0) {
            end = string.length() + end;
        }

        if (end > string.length()) {
            end = string.length();
        }

        if (start > end) {
            return EMPTY;
        }


        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return string.substring(start, end);

    }

    public static String[] split(final String string) {
        return split(string, SPACE);
    }

    public static String[] split(final String string, final String separator) {
        if (string == null) {
            return null;
        }

        if (separator == null) {
            return string.split(SPACE);
        }

        return string.split(separator);
    }


    public static String join(String[] strings, String split) {
        if (strings == null) {
            return null;
        }
        int length = strings.length;
        if (length == 0) {
            return EMPTY;
        }
        if (split == null) {
            split = "";
        }
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string).append(split);
        }

        return builder.substring(0, builder.length() - split.length());
    }

    public static String join(List<String> strings, String split) {
        String[] array = ArrayUtils.toArray(strings, String.class);
        return join(array, split);
    }

    public static String[] repeat(String str, int count) {
        String[] strs = new String[count];
        for (int i = 0; i < count; i++) {
            strs[i] = str;
        }
        return strs;
    }

    public static String format(String str, String... strs) {
        if (str == null) {
            return null;
        }

        if (strs == null || strs.length == 0) {
            return str;
        }

        List<Integer> indexes = new LinkedList<>();
        int index = 0;
        while (true) {
            index = str.indexOf(BRACKET, index);
            if (index == -1) {
                break;
            }
            indexes.add(index);
            index += 2;
        }

        if (strs.length != indexes.size()) {
            throw new RuntimeException();
        }

        StringBuilder sb = new StringBuilder();
        int start = 0;
        for (int i = 0; i < strs.length; i++) {
            int close = indexes.get(i);
            sb.append(str, start, close);
            sb.append(strs[i]);
            start = close + 2;
        }

        sb.append(str, start, str.length());

        return sb.toString();
    }

    public static boolean isNumeric(final CharSequence sequence) {
        if (isEmpty(sequence)) {
            return false;
        }

        final int length = sequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(sequence.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isWhitespace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

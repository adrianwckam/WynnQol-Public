package net.fabricmc.wynnqol.Publicized.Utils;

import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.Optional;

// ctrl c + v from from wynntils
public class TextUtils {
    public static String getCoded(Text component) {
        StringBuilder result = new StringBuilder();

        component.visit(new CodedStringGenerator(result), Style.EMPTY);

        return result.toString();
    }

    public static Optional<Formatting> getChatFormatting(TextColor textColor) {
        return getChatFormatting(textColor.getRgb());
    }

    public static Optional<Formatting> getChatFormatting(int textColor) {
        return Arrays.stream(Formatting.values())
                .filter(c -> c.isColor() && textColor == c.getColorValue())
                .findFirst();
    }

    private static StringBuilder tryConstructDifference(Style oldStyle, Style newStyle) {
        StringBuilder add = new StringBuilder();

        int oldColorInt = Optional.ofNullable(oldStyle.getColor()).map(TextColor::getRgb).orElse(-1);
        int newColorInt = Optional.ofNullable(newStyle.getColor()).map(TextColor::getRgb).orElse(-1);

        if (oldColorInt == -1) {
            if (newColorInt != -1) {
                getChatFormatting(newColorInt).ifPresent(add::append);
            }
        } else if (oldColorInt != newColorInt) {
            return null;
        }

        if (oldStyle.isBold() && !newStyle.isBold()) return null;
        if (!oldStyle.isBold() && newStyle.isBold()) add.append(Formatting.BOLD);

        if (oldStyle.isItalic() && !newStyle.isItalic()) return null;
        if (!oldStyle.isItalic() && newStyle.isItalic()) add.append(Formatting.ITALIC);

        if (oldStyle.isUnderlined() && !newStyle.isUnderlined()) return null;
        if (!oldStyle.isUnderlined() && newStyle.isUnderlined()) add.append(Formatting.UNDERLINE);

        if (oldStyle.isStrikethrough() && !newStyle.isStrikethrough()) return null;
        if (!oldStyle.isStrikethrough() && newStyle.isStrikethrough()) add.append(Formatting.STRIKETHROUGH);

        if (oldStyle.isObfuscated() && !newStyle.isObfuscated()) return null;
        if (!oldStyle.isObfuscated() && newStyle.isObfuscated()) add.append(Formatting.OBFUSCATED);

        return add;
    }

    private static final class CodedStringGenerator implements StringVisitable.StyledVisitor<Object> {
        private final StringBuilder result;
        Style oldStyle;

        private CodedStringGenerator(StringBuilder result) {
            this.result = result;
            oldStyle = Style.EMPTY;
        }

        @Override
        public Optional<Object> accept(Style style, String string) {
            handleStyleDifference(oldStyle, style, result);
            result.append(string);

            oldStyle = style;

            return Optional.empty();
        }

        /**
         * This method handles the fact that the style likely has changed between 2 Texts
         *
         * <p>It tries to first generate a constructive way of adding color codes to get from the old
         * style to the new style. If that does not succeed, it instead resets the format if the old style was not empty, and adds the
         * color codes of the new style
         */
        private static void handleStyleDifference(Style oldStyle, Style newStyle, StringBuilder result) {
            if (oldStyle.equals(newStyle)) return;

            if (!oldStyle.isEmpty()) {
                StringBuilder different = tryConstructDifference(oldStyle, newStyle);

                if (different != null) {
                    result.append(different);
                    return;
                }

                result.append(Formatting.RESET);
            }

            if (newStyle.getColor() != null) {
                getChatFormatting(newStyle.getColor()).ifPresent(result::append);
            }

            if (newStyle.isBold()) result.append(Formatting.BOLD);
            if (newStyle.isItalic()) result.append(Formatting.ITALIC);
            if (newStyle.isUnderlined()) result.append(Formatting.UNDERLINE);
            if (newStyle.isStrikethrough()) result.append(Formatting.STRIKETHROUGH);
            if (newStyle.isObfuscated()) result.append(Formatting.OBFUSCATED);
        }
    }
}

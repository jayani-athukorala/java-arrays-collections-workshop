package se.lexicon.menu;

/**
 * Utility class to build a formatted menu string for console display.
 */
public class MenuTextBuilder {

    /**
     * Builds a text-based menu with a title and an array of menu options.
     *
     * @param title   The title to display at the top of the menu
     * @param options Array of MenuOption objects to display as choices
     * @return Formatted menu string
     */
    public static String buildMenu(String title, MenuOption[] options) {

        StringBuilder sb = new StringBuilder();

        // Menu header
        sb.append("======== ").append(title).append(" ========\n");

        // List all menu options with code and text
        for (MenuOption option : options) {
            sb.append(option.getCode())
                    .append(". ")
                    .append(option.getText())
                    .append("\n");
        }

        // Menu footer
        sb.append("==========================================\n");
        sb.append("Choose option: "); // Prompt for user input

        return sb.toString();
    }
}

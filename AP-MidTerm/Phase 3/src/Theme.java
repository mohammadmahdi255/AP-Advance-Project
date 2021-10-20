import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Theme {

    private String[] fileAddress;
    private String addressIcons;


    private Color[][] buttonTheme;
    private Color[][] labelTheme;
    private Color[][] textFieldTheme;

    private Color[] insomniaTheme;
    private Color[] comboBoxTheme;
    private Color[] backGroundTheme;
    private Color[] methodColors;


    /**
     * constructor of this class
     */
    public Theme () {

        fileAddress = new String[2];
        fileAddress[0] = "src/Themes/Default/Theme Color.txt";
        fileAddress[1] = "src/Themes/White Theme/Theme Color.txt";

        buttonTheme = new Color[2][4];
        labelTheme = new Color[5][4];

        insomniaTheme = new Color[4];
        textFieldTheme = new Color[3][4];
        comboBoxTheme = new Color[4];
        backGroundTheme  = new Color[3];
        methodColors = new Color[8];

        setTheme(0);

    }

    /**
     * read a theme file
     * @param index of the theme field
     */
    public void setTheme(int index) {

        FileReader themeColor;
        Scanner reader;

        try {
            themeColor = new FileReader(fileAddress[index]);
            reader = new Scanner(themeColor);
        } catch (IOException ignored){
            return;
        }

        addressIcons = reader.nextLine();

        for (int i = 0; i < 4; i++) {
            insomniaTheme[i] = new Color(reader.nextInt(),reader.nextInt(),reader.nextInt());
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                textFieldTheme[i][j] = new Color(reader.nextInt(), reader.nextInt(), reader.nextInt());
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                buttonTheme[i][j] = new Color(reader.nextInt(),reader.nextInt(),reader.nextInt());
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                labelTheme[i][j] = new Color(reader.nextInt(),reader.nextInt(),reader.nextInt());
            }
        }

        for (int i = 0; i < 4; i++) {
            comboBoxTheme[i] = new Color(reader.nextInt(),reader.nextInt(),reader.nextInt());
        }

        for (int i = 0; i < 3; i++) {
            backGroundTheme[i] = new Color(reader.nextInt(),reader.nextInt(),reader.nextInt());
        }

        for (int i = 0; i < 8; i++) {
            methodColors[i] = new Color(reader.nextInt(),reader.nextInt(),reader.nextInt());
        }

        try {
            themeColor.close();
        } catch (IOException ignored){
        }

    }

    /**
     *
     * @return the Icons address file
     */
    public String getAddressIcons() {
        return addressIcons;
    }

    /**
     *
     * @return the theme color of insomnia
     */
    public Color[] getInsomniaTheme() {
        return insomniaTheme;
    }

    /**
     *
     * @return the theme color of text field
     */
    public Color[][] getTextFieldTheme() {
        return textFieldTheme;
    }

    /**
     *
     * @return the theme color of button field
     */
    public Color[][] getButtonTheme() {
        return buttonTheme;
    }

    /**
     *
     * @return the theme color of label field
     */
    public Color[][] getLabelTheme() {
        return labelTheme;
    }

    /**
     *
     * @return the theme color of combobox field
     */
    public Color[] getComboBoxTheme() {
        return comboBoxTheme;
    }

    /**
     *
     * @return the theme color of background field
     */
    public Color[] getBackGroundTheme() {
        return backGroundTheme;
    }

    /**
     *
     * @return the theme color of method color
     */
    public Color[] getMethodColors() {
        return methodColors;
    }

}

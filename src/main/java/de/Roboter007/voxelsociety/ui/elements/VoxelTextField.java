package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class VoxelTextField extends VoxelElement {

    private String input;
    public String selectedInput;

    private final UiStyle uiStyle;
    private final Font font;
    private final Color cursorColor;


    private int x;
    private int y;
    private int width;
    private int height;
    private final int maxCharLength;

    private final boolean enableGhostText;
    private String ghostText;


    public boolean selected;
    private int selectedChar;
    private int animation = 0;
    public boolean capsLocked = false;
    private final Consumer<String> stringConsumer;

    public VoxelTextField(String input, UiStyle uiStyle, int x, int y, int width, int maxCharLength, boolean enableGhostText, Color cursorColor, Consumer<String> stringConsumer) {
        if(enableGhostText) {
            this.input = "";
            this.ghostText = input;
        } else {
            this.input = input;
        }
        this.uiStyle = uiStyle;
        this.font = uiStyle.getFont();
        this.x = x;
        this.y = y;
        this.width = width;

        //ToDo: Fix TextField Bug.....
        FontRenderContext frc = new FontRenderContext(font.getTransform(), true, true);
        TextLayout textLayout = new TextLayout(input, font, frc);
        this.height = (int)textLayout.getBounds().getHeight();


        this.maxCharLength = maxCharLength;
        this.selectedChar = this.input.length();
        this.enableGhostText = enableGhostText;
        this.cursorColor = cursorColor;
        this.stringConsumer = stringConsumer;
    }

    public VoxelTextField(String input, UiStyle uiStyle, int x, int y, int width, int maxCharLength, Consumer<String> stringConsumer) {
        this(input, uiStyle, x, y, width, maxCharLength, false, Color.BLUE, stringConsumer);
    }

    public VoxelTextField(String input, UiStyle uiStyle, int x, int y, int width, int maxCharLength, Color color, Consumer<String> stringConsumer) {
        this(input, uiStyle, x, y, width, maxCharLength, false, color, stringConsumer);
    }

    public VoxelTextField(String input, UiStyle uiStyle, int x, int y, int width, int maxCharLength, boolean enableGhostText, Consumer<String> stringConsumer) {
        this(input, uiStyle, x, y, width, maxCharLength, enableGhostText, Color.BLUE, stringConsumer);
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        /*if(this.autoCalcWidth && this.width == -1) {
            this.width = ((int) font.getStringBounds(this.input, graphics2D.getFontRenderContext()).getWidth() * this.maxCharLength) + 3;
        }*/

        this.height = (int) font.getStringBounds(this.input, graphics2D.getFontRenderContext()).getHeight();
        graphics2D.setFont(this.uiStyle.getFont());
        if(!this.isMouseOverElement()) {
            graphics2D.setColor(Color.BLACK);
        } else {
            graphics2D.setColor(Color.DARK_GRAY);
        }
        graphics2D.fill(getBoundingBox());

        graphics2D.setColor(Color.WHITE);
        graphics2D.draw(getBoundingBox());

        if(selectedText()) {
            graphics2D.setColor(Color.LIGHT_GRAY);
            int selHeight = (int) font.getStringBounds(this.selectedInput, graphics2D.getFontRenderContext()).getHeight();
            int selWidth = (int) font.getStringBounds(this.selectedInput, graphics2D.getFontRenderContext()).getWidth();
            graphics2D.fillRect(x, y, selWidth + 2, selHeight);
        }


        drawText(graphics2D);

        drawTextInputCursor(graphics2D);
    }

    //ToDo: Fix Accents und andere besondere Symbole (+ und - sowie ein paar andere sind schon gefixed)
    public void drawText(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.setColor(this.uiStyle.getColorPalette().textColor());
        graphics2D.drawString(input, x + 2, y + height - 1);

        if(enableGhostText && !ghostText.isEmpty() && input.isEmpty() && !selected) {
            graphics2D.drawString(ghostText, x + 2, y + height - 1);
        }
    }

    public void drawTextInputCursor(Graphics2D graphics2D) {
        if(selected) {
            if (animation > 49) {

                int x;
                if(input.isEmpty()) {
                    x = this.x + 3;
                } else {
                    String currentTextPos = this.input.substring(0, this.selectedChar);
                    x = this.x + 3 + (int) font.getStringBounds(currentTextPos, graphics2D.getFontRenderContext()).getWidth();
                }

                graphics2D.setColor(this.cursorColor);
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.drawLine(x, y + 3, x, y + height - 3);
                if(animation > 99) {
                    animation = 0;
                }
                graphics2D.setStroke(new BasicStroke(0));
            }
            animation++;
        } else {
            animation = 0;
        }
    }

    @Override
    public boolean customMousePress(MouseEvent e) {
        if(this.isMouseOverElement()) {
            if (isRightClick(e) || isLeftClick(e) || isMiddleClick(e)) {
                selected = true;
            }
        } else {
            if (isRightClick(e) || isLeftClick(e) || isMiddleClick(e)) {
                selected = false;
            }
        }
        return true;
    }

    @Override
    public boolean customKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(selected) {
            if (code == KeyEvent.VK_CAPS_LOCK) {
                this.capsLocked = !this.capsLocked;
            } else if (code == KeyEvent.VK_BACK_SPACE) {
                if (selectedText()) {
                    this.input = "";
                    this.selectedInput = null;
                } else {
                    if(!input.isEmpty()) {
                        this.input = this.input.substring(0, selectedChar - 1) + this.input.substring(selectedChar);
                        selectedChar--;
                    }
                }
            } else if (code == KeyEvent.VK_SPACE) {
                if (selectedText()) {
                    this.input = " ";
                    this.selectedInput = null;
                } else {
                    addInput(" ");
                }
            } else if (code == KeyEvent.VK_SHIFT) {
                return true;
            } else if (code == KeyEvent.VK_A && e.isControlDown()) {
                this.selectedInput = this.input;
            } else if (code == KeyEvent.VK_C && e.isControlDown()) {
                if (selectedInput != null && !this.selectedInput.isEmpty()) {
                    StringSelection stringSelection = new StringSelection(this.selectedInput);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    this.selectedInput = null;
                }
            } else if (code == KeyEvent.VK_V && e.isControlDown()) {
                String newInput = "";
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                Transferable transferable = clipboard.getContents(null);
                if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        newInput = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                    } catch (IOException | UnsupportedFlavorException ex) {
                        System.out.println(ex.fillInStackTrace().getMessage());
                    }
                }

                if (!newInput.isEmpty()) {
                    if (selectedText()) {
                        if (newInput.length() < maxCharLength) {
                            this.input = newInput;
                            this.selectedInput = null;
                        }
                    } else {
                        this.input = this.input + newInput;
                    }
                }

            } else if (e.isControlDown()) {
                return true;
            } else if (code == KeyEvent.VK_LEFT) {
                if(selectedChar > 0) {
                    selectedChar--;
                }
            } else if (code == KeyEvent.VK_RIGHT) {
                if(selectedChar < input.length()) {
                    selectedChar++;
                }
            } else {
                if (!e.isActionKey() && input.length() < this.maxCharLength) {
                    String scaledText = getKeyText(e);
                    if (selectedText()) {
                        this.input = scaledText;
                        this.selectedInput = null;
                    } else {
                        this.input = input + scaledText;
                    }

                    selectedChar++;
                }
            }
            this.updateText();
            return true;
        }
        return false;
    }

    @Override
    public void updateBoundingBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private String getKeyText(KeyEvent e) {
        String scaledText;
        String text = String.valueOf(e.getKeyChar());
        if(e.getKeyCode() == KeyEvent.VK_BACK_QUOTE) {
            text = "´";
        }
        if (e.isShiftDown() || capsLocked) {
            scaledText = text.toUpperCase();
        } else {
            scaledText = text.toLowerCase();
        }
        return scaledText;
    }

    public void addInput(String newInput) {
        String modInput = this.input + newInput;
        if(modInput.length() < this.maxCharLength) {
            this.input = modInput;
        }
    }

    public boolean selectedText() {
        return this.selectedInput != null && !selectedInput.isEmpty();
    }

    public void updateText() {
        this.stringConsumer.accept(this.input);
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(x, y, width, height);
    }
}

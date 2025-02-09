package de.Roboter007.voxelsociety.ui.elements.slider;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.ui.elements.VoxelElement;
import de.Roboter007.voxelsociety.ui.elements.listener.VoxelElementHandler;
import de.Roboter007.voxelsociety.utils.NumCalc;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;

import static de.Roboter007.voxelsociety.ui.elements.slider.ScrollDirection.HORIZONTAL;

public class VoxelSlider<N extends Number> extends VoxelElement {
    private final ScrollDirection scrollDirection;
    private N number;

    private final int sliderWidth = 20;

    private final N scroll;
    private final N maxRange;
    private final N minRange;

    private final String text;
    private final UiStyle uiStyle;
    private int x;
    private int y;
    private int width;
    private int height;

    private final Consumer<N> consumer;
    private final NumCalc<N> numberCalc = new NumCalc<>();

    private boolean sliderPressed;

    public VoxelSlider(ScrollDirection ScrollDirection, String text, UiStyle uiStyle, int x, int y, int width, int height, N minRange, N maxRange, N defaultNumber, N scroll, Consumer<N> consumer) {
        super();
        this.scrollDirection = ScrollDirection;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.number = defaultNumber;
        this.scroll = scroll;

        this.text = text;
        this.uiStyle = uiStyle;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;

        this.consumer = consumer;
    }

    public VoxelSlider(ScrollDirection ScrollDirection, String text, UiStyle uiStyle, int x, int y, int height, N minRange, N maxRange, N defaultNumber, N scroll, Consumer<N> consumer) {
        this(ScrollDirection, text, uiStyle, x, y, maxRange.intValue(), height, minRange, maxRange, defaultNumber, scroll, consumer);
    }

    public boolean isMouseOverSlider() {
        return getSlider().contains(VoxelElementHandler.TRACKED_MOUSE_POSITION);
    }

    public boolean isMouseOverBB() {
        return getBoundingBox().contains(VoxelElementHandler.TRACKED_MOUSE_POSITION);
    }

    @Override
    public boolean isMouseOverElement() {
        return isMouseOverBB() || isMouseOverSlider();
    }


    public double getSizeFactor() {
        return (double) width / maxRange.intValue();
    }

    public int toVisualPos(int originalPos) {
        return (int) (getSizeFactor() * originalPos);
    }

    public int toOriginalPos(int visualPos) {
        return (int) (visualPos / getSizeFactor());
    }

    public boolean checkMismatch(Rectangle2D stringBounds) {
        return !(stringBounds.getWidth() >= this.getBoundingBox().width);
    }


    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();

        graphics2D.setColor(Color.BLACK);
        graphics2D.fill(this.getBoundingBox());

        graphics2D.setColor(Color.WHITE);
        graphics2D.draw(this.getBoundingBox());

        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fill(this.getSlider());

        if(sliderPressed) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.draw(this.getSlider());
        }

        //ToDo: make the size of string matching the size of the Slider

        Rectangle2D stringBounds = this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext());

        if(this.scrollDirection == HORIZONTAL) {
            if (this.text != null) {
                String text = this.text + number;
                int x = (this.x + ((this.height / 2)) - ((int) stringBounds.getHeight() / 2) + 5);
                int y = (this.y + ((this.width / 2)) - ((int) stringBounds.getWidth() / 2));

                uiUtilities.drawString(text, uiStyle, false, x, y, 90);
            }
        } else {
            if (this.text != null) {
                String text = this.text + number;
                int x = (this.x + ((this.width / 2) - ((int) stringBounds.getWidth() / 2))) - 2;
                int y = (this.y + ((this.height / 2) + ((int) stringBounds.getHeight() / 2))) - 2;


                uiUtilities.drawString(text, uiStyle, false, x, y);
            }
        }
    }

    @Override
    public Rectangle getBoundingBox() {
        if(scrollDirection == HORIZONTAL) {
            return new Rectangle(this.x, this.y, height, width + sliderWidth);
        } else {
            return new Rectangle(this.x, this.y, this.width + sliderWidth, height);
        }
    }

    public Rectangle getSlider() {
        if(scrollDirection == HORIZONTAL) {
            return new Rectangle(this.x + 1, this.y + toVisualPos(number.intValue()), height - 1, sliderWidth);
        } else {
            return new Rectangle(this.x + toVisualPos(number.intValue()), this.y + 1, sliderWidth, height - 1);
        }
    }

    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void updateBoundingBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean customMouseWheelMove(MouseWheelEvent e) {
        if(this.isMouseOverElement()) {
            N num;
            if(e.isControlDown()) {
                num = numberCalc.addition(this.number, numberCalc.multiply((N) (Integer) 10, e.getWheelRotation()));
            } else {
                num = numberCalc.addition(this.number, numberCalc.multiply(scroll, e.getWheelRotation()));
            }

            if (checkRange(num)) {
                this.number = num;
                updatePos();
            }

        }
        return true;
    }


    @Override
    public boolean customMousePress(MouseEvent e) {
        if(isLeftClick(e) || isRightClick(e)) {
            this.sliderPressed = this.getSlider().contains(e.getPoint());
        }


        return true;
    }

    @Override
    public boolean customMouseRelease(MouseEvent e) {
        if(isLeftClick(e) || isRightClick(e)) {
            this.sliderPressed = false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean customMouseClick(MouseEvent e) {
        Point clickedPos = e.getPoint();
        N num;
        if(this.isMouseOverElement()) {
            if (scrollDirection == HORIZONTAL) {
                num = (N) ((Integer) toOriginalPos((clickedPos.y - this.getBoundingBox().y)));
            } else {
                num = (N) ((Integer) toOriginalPos((clickedPos.x - this.getBoundingBox().x)));
            }

            if(checkRange(num)) {
                this.number = num;
                this.updatePos();
            }
        }
        return true;
    }



    @SuppressWarnings("unchecked")
    @Override
    public boolean customMouseDrag(MouseEvent e) {
        Point clickedPos = e.getPoint();
        N num;
        if (this.sliderPressed) {
            if (scrollDirection == HORIZONTAL) {
                num = (N) ((Integer) toOriginalPos((clickedPos.y - this.getBoundingBox().y)));
            } else {
                num = (N) ((Integer) toOriginalPos((clickedPos.x - this.getBoundingBox().x)));
            }

            if (checkRange(num)) {
                this.number = num;
                this.updatePos();
            }
        }

        return true;
    }

    public void updatePos() {
        this.consumer.accept(number);
    }

    public boolean checkRange(N number) {
        Class<? extends Number> cls = number.getClass();
        Class<? extends Number> cls2 = maxRange.getClass();
        Class<? extends Number> cls3 = minRange.getClass();

        if (cls == Integer.class && cls2 == Integer.class && cls3 == Integer.class) {
            return number.intValue() <= maxRange.intValue() && number.intValue() >= minRange.intValue();
        }
        if (cls == Long.class && cls2 == Long.class && cls3 == Long.class) {
            return number.longValue() <= maxRange.longValue() && number.longValue() >= minRange.longValue();
        }
        if(cls == Double.class && cls2 == Double.class && cls3 == Double.class) {
            return number.doubleValue() <= maxRange.doubleValue() && number.doubleValue() >= minRange.doubleValue();
        }
        if(cls == Float.class && cls2 == Float.class && cls3 == Float.class) {
            return number.floatValue() <= maxRange.floatValue() && number.floatValue() >= minRange.floatValue();
        }
        throw new UnsupportedOperationException("unknown class: " + cls);
    }
}

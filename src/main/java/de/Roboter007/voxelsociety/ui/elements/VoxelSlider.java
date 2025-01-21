package de.Roboter007.voxelsociety.ui.elements;

import de.Roboter007.voxelsociety.ui.UiStyle;
import de.Roboter007.voxelsociety.ui.UiUtilities;
import de.Roboter007.voxelsociety.utils.NumCalc;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class VoxelSlider<N extends Number> extends VoxelElement {
    private final Direction direction;
    private final N moveCount;
    private final N maxRange;
    private final N minRange;
    private N number;

    private final int sliderWidth = 20;

    private final String text;
    private final UiStyle uiStyle;
    private int x;
    private int y;
    private int width;
    private int height;
    private final int ratio;

    private final Consumer<N> consumer;
    private final NumCalc<N> numberCalc = new NumCalc<>();


    private boolean selected;
    private boolean sliderPressed;
    private boolean boxPressed;

    public VoxelSlider(Direction direction, String text, UiStyle uiStyle, int x, int y, int height, int ratio, N minRange, N maxRange, N defaultNumber, N moveCount, Consumer<N> consumer) {
        super();
        this.direction = direction;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.moveCount = moveCount;
        this.number = defaultNumber;
        this.width = maxRange.intValue() * ratio;
        this.ratio = ratio;

        this.text = text;
        this.uiStyle = uiStyle;
        this.x = x;
        this.y = y;
        this.height = height;
        this.consumer = consumer;
    }

    public VoxelSlider(Direction direction, String text, UiStyle uiStyle, int x, int y, int height, N minRange, N maxRange, N defaultNumber, N moveCount, Consumer<N> consumer) {
        this(direction, text, uiStyle, x, y, height, 1, minRange, maxRange, defaultNumber, moveCount, consumer);
    }

    @Override
    public void draw(UiUtilities uiUtilities) {
        Graphics2D graphics2D = uiUtilities.getGraphics2D();
        if(!this.selected) {
            graphics2D.setColor(Color.BLACK);
        } else {
            graphics2D.setColor(Color.DARK_GRAY);
        }

        graphics2D.fill(this.getBoundingBox());

        graphics2D.setColor(Color.RED);
        graphics2D.fill(this.getSlider());
        if(sliderPressed) {
            graphics2D.setColor(Color.WHITE);
            //graphics2D.drawRect(this.getSlider().x, this.getSlider().y, this.getSlider().width, this.getSlider().height);
            graphics2D.draw(this.getSlider());
        }

        graphics2D.setColor(Color.WHITE);
        graphics2D.draw(this.getBoundingBox());


        if(this.text != null) {
            String text = this.text + number;
            int x = (this.x + ((this.width / 2) - ((int) this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext()).getWidth() / 2))) - 2;
            int y = (this.y + ((this.height / 2) + ((int) this.uiStyle.getFont().getStringBounds(this.text, graphics2D.getFontRenderContext()).getHeight() / 2))) - 2;

            uiUtilities.drawString(text, uiStyle, false, x, y);
        }

    }

    @Override
    public Rectangle getBoundingBox() {
        if(direction == Direction.HORIZONTAL) {
            return new Rectangle(this.x, this.y, height, width + sliderWidth);
        } else {
            return new Rectangle(this.x, this.y, this.width + sliderWidth, height);
        }
    }

    @Override
    public boolean customMouseMove(MouseEvent e) {
        this.selected = this.getSlider().contains(e.getPoint()) || this.getBoundingBox().contains(e.getPoint());
        return true;
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

    public Rectangle getSlider() {
        if(direction == Direction.HORIZONTAL) {
            return new Rectangle(this.x, this.y + number.intValue(), height, sliderWidth);
        } else {
            return new Rectangle(this.x + number.intValue(), this.y, sliderWidth, height);
        }
    }

    @Override
    public boolean customMouseWheelMove(MouseWheelEvent e) {
        if(this.getSlider().contains(e.getPoint()) || this.getBoundingBox().contains(e.getPoint())) {
            N num = numberCalc.addition(this.number, numberCalc.multiply(this.moveCount, e.getWheelRotation()));
            if (checkRange(num)) {
                this.number = num;
                updatePos();
            }

        }
        return true;
    }


    @Override
    public boolean customMousePress(MouseEvent e) {
        prevX = e.getXOnScreen();
        prevY = e.getYOnScreen();

        if(selected && isLeftClick(e) || isRightClick(e)) {
            this.sliderPressed = this.getSlider().contains(e.getPoint());
            this.boxPressed = this.getBoundingBox().contains(e.getPoint());
        }

        return true;
    }

    @Override
    public boolean customMouseRelease(MouseEvent e) {
        if(selected && isLeftClick(e) || isRightClick(e)) {
            if(this.getSlider().contains(e.getPoint())) {
                this.sliderPressed = false;
            } else if (this.getBoundingBox().contains(e.getPoint())) {
                this.boxPressed = false;
            } else {
                this.boxPressed = false;
                this.sliderPressed = false;
            }
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean customMouseClick(MouseEvent e) {
        Point clickedPos = e.getPoint();
        if(this.selected) {
            if(direction == Direction.HORIZONTAL) {
                this.number = (N) ((Integer) (clickedPos.y - this.getBoundingBox().y));
                updatePos();
            } else {
                this.number = (N) ((Integer) (clickedPos.x - this.getBoundingBox().x));
                updatePos();
            }

        }
        return true;
    }

    int prevX, prevY;


    @Override
    public boolean customMouseDrag(MouseEvent e) {
        if(this.getSlider().contains(e.getPoint()) && sliderPressed) {
            if(direction == Direction.HORIZONTAL) {
                int y = e.getYOnScreen();
                if (y < prevY) {
                    N num = numberCalc.subtract(this.number, this.moveCount);
                    if (checkRange(num)) {
                        this.number = num;
                        updatePos();
                    }
                } else if (y > prevY) {
                    N num = numberCalc.addition(this.number, this.moveCount);
                    if (checkRange(num)) {
                        this.number = num;
                        updatePos();
                    }
                }
            } else {
                int x = e.getXOnScreen();
                if (x < prevX) {
                    N num = numberCalc.subtract(this.number, this.moveCount);
                    if (checkRange(num)) {
                        this.number = num;
                        updatePos();
                    }
                } else if (x > prevX) {
                    N num = numberCalc.addition(this.number, this.moveCount);
                    if (checkRange(num)) {
                        this.number = num;
                        updatePos();
                    }
                }
            }

            prevX = e.getXOnScreen();
            prevY = e.getYOnScreen();
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

    public enum Direction {
        VERTICAL,
        HORIZONTAL;

    }

}

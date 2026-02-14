# Java 2D Drawing Application

An interactive drawing program built with Java Swing and the Java 2D API. Draw lines, ovals, and rectangles with customizable colors, gradients, strokes, and dashing.

## Features

- **Three shape types** — Line, Oval, Rectangle
- **Dual color picker** with optional gradient fill
- **Adjustable stroke** — line width and dash length with dashed/solid toggle
- **Filled or outlined** bounded shapes
- **Live preview** — see the shape while dragging
- **Undo / Clear** — remove the last shape or wipe the canvas
- **Anti-aliased rendering** for smooth edges
- **Status bar** showing real-time mouse coordinates

## Getting Started

### Prerequisites

- Java 8 or later (JDK)

### Compile & Run

```bash
javac -d out src/drawingapp/*.java
java -cp out drawingapp.DrawingApp
```

## How to Use

1. Select a shape from the dropdown
2. Choose colors with the **1st Color** and **2nd Color** buttons
3. Toggle **Use Gradient** to blend between the two colors
4. Adjust **Line Width** and **Dash Length** spinners; toggle **Dashed** for dashed strokes
5. Check **Filled** to fill ovals and rectangles
6. Click and drag on the white canvas to draw
7. Use **Undo** to remove the last shape or **Clear** to start over

## Project Structure

```
Java2DDrawingApp/
├── src/
│   └── drawingapp/
│       ├── DrawingApp.java              — Entry point
│       ├── DrawingApplicationFrame.java — Main frame, toolbar, event handling
│       ├── MyShapes.java                — Abstract base (point, paint, stroke)
│       ├── MyBoundedShapes.java         — Abstract base for bounded shapes
│       ├── MyLine.java                  — Line segment
│       ├── MyOval.java                  — Ellipse
│       └── MyRectangle.java            — Rectangle
├── .gitignore
└── README.md
```

## Architecture

```
MyShapes (abstract)
├── MyLine
└── MyBoundedShapes (abstract)
    ├── MyOval
    └── MyRectangle
```

The `DrawingApplicationFrame` acts as both the view and the controller. A private inner `DrawPanel` class handles mouse events and rendering. Shapes are stored in an `ArrayList` and drawn polymorphically through `MyShapes.draw(Graphics2D)`.

## License

This project is released under the [MIT License](https://opensource.org/licenses/MIT).

# Towers of Hanoi (GUI Version)

A Java Graphical User Interface (GUI) implementation of the classic Towers of Hanoi puzzle.

This repository is split into two distinct branches:
- **`GUI-version`**: Graphical user interface implementation utilizing Java Swing (current branch).
- **`CLI-version`**: Text-based interactive command-line implementation.

## How to Play (GUI Version)

### Compilation & Execution
The project uses a standard `Makefile` for compilation and execution. Ensure you have the Java Development Kit (JDK) installed, and then run:

```bash
make run
```

*Note: You can clean up the compiled binary files at any time using `make clean`.*

### Game Flow & Controls
1. **Initial Setup:** Once the game starts, a dialog box will prompt you to input the **number of disks** to play with (between 2 and 9).
2. **Main Interface:** The main window will launch, presenting a graphical view of the three poles, a wooden base, and randomized brightly colored disks.
3. **Making a Move:** Use the input field at the top of the window labeled `Move (e.g., 1 3):`
   - Type your move in the format: `SourceRod DestinationRod` (separated by a space).
   - *Example: In order to move the top disk from the first rod over to the third rod, type `1 3`.*
   - Press the **Enter** key or click the **Submit** button to execute the move.
4. **Restarting:** You can reset the game board at any time by clicking the **Restart** button located in the top-left corner.

### Graphical Features
- **Dynamic Resizing:** The disk widths mathematically scale based on the total number of disks you choose to play with. Each disk also features a gradient filling and dynamically centered numbering.
- **Victory Screen Overlay:** A translucent dimming effect and a bright yellow "WELL DONE!" message drops onto the screen the moment you conquer the puzzle.

### Rules
1. Only **one disk** can be moved at a time.
2. Only the **topmost disk** of a rod can be moved.
3. A **larger disk** cannot be placed on top of a **smaller disk**.

### Goal
Successfully relocate the entire stack of disks from the first rod to the last rod (Rod 3).

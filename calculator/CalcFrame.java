package calculator;

/*
 * This calculator records an expression as the string "exp" and then when requested by the user, parses this string
 * and then performs the required calculations to produce the desired answer.  Brackets are handled recursively with 
 * the Calculate function being called repeatedly in cases of nested brackets.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class CalcFrame extends Frame {

	// This string will hold the mathematical expression the user enters into
	// the calculator. This string is formatted in a way so that the calculator
	// can understand the operations required of it.
	String exp = new String();

	// This string is formatted to be presented to the user.
	String screenString;

	// This string is used to store the previous answer calculated.
	String prevAns = "0";

	// sText is used to display results to the user.
	TextArea sText = new TextArea("", 0, 0, TextArea.SCROLLBARS_NONE);

	// This variable is a flag set to true when a calculation has just been
	// performed Used to control calculator behavior.
	Boolean calcDone = new Boolean(false);

	// This object is used to store the graphic for the calculator.
	BufferedImage casioSmallImage;

	// used for moving of the gui
	int tempX;
	int tempY;

	// The constructor for the class
	public CalcFrame() {

		try {
			// Reading the image
			casioSmallImage = ImageIO.read(this.getClass().getResource(
					"/CasioSmallTrans.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		add(sText);

		// Basic configuration of the frame.
		setName("Calculator v03");
		setSize(256, 512);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 5,
				Toolkit.getDefaultToolkit().getScreenSize().height / 5);
		setLayout(null);
		setResizable(false);
		setUndecorated(true);
		setFocusable(true);

		// The Calculator Screen placement and configuration.
		sText.setSize(194, 75);
		sText.setLocation(30, 80);
		sText.setVisible(true);
		sText.setBackground(Color.white);
		sText.setFont(new Font("Times New Roman", 0, 20));
		sText.setEditable(false);
		sText.setFocusable(false);

		// This is the mouse listener for the window. This is where logic
		// relating to mouse clicks is handled.

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent mouse) {
				tempX = mouse.getX();
				tempY = mouse.getY();
				buttonMapper(mouse.getPoint());

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {

				// Move the frame to the location the user drags their mouse to
				setLocation(e.getXOnScreen() - tempX, e.getYOnScreen() - tempY);

			}
		});

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent key) {

			}

			@Override
			public void keyReleased(KeyEvent key) {

			}

			// This key listener checks to see if the user has hit escape. If
			// not, it provides the keyMapper function with a key to work with.
			@Override
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				keyMapper(key);

			}
		});

	}

	public void paint(Graphics g) {
		super.paint(g);
		Color col = new Color(255, 255, 255, 0);
		g.drawImage(casioSmallImage, 0, 0, col, null);

	};

	// When displaying a string to the user the screenString is displayed. This
	// function copies the "exp" string and then
	// formats it for presentation. It finally sets the sText text area to
	// display the string.

	public void screenUpdate(String s) {

		screenString = exp;

		screenString = screenString.replaceAll("p@", "π");
		screenString = screenString.replaceAll("\\+@", "+");
		screenString = screenString.replaceAll("-@", "-");
		screenString = screenString.replaceAll("\\*@", "*");
		screenString = screenString.replaceAll("/@", "/");
		screenString = screenString.replaceAll("\\^@", "^");
		screenString = screenString.replaceAll("l@", "log");
		screenString = screenString.replaceAll("x@", "e");
		screenString = screenString.replaceAll("r@", "sqrt");
		screenString = screenString.replaceAll("s@", "sin");
		screenString = screenString.replaceAll("c@", "cos");
		screenString = screenString.replaceAll("t@", "tan");

		sText.setText(screenString);
	}

	// This calculator will operate using the logic order of Brackets, Other
	// operations, Division, Multiplication, Addition, Subtraction.
	// Once given a string, this function will find the solution to the
	// expression and then output the results to the calculator screen.
	public String Calculate(String s) {

		/*
		 * This calculator handles brackets recursively. It first checks to see
		 * if there are any brackets. If there are, it increments q and continues checking for the
		 * corresponding closing bracket.  Once the closing bracket has been found, it calls itself
		 * again on the contents of the brackets and continues like this until all brackets are
		 * removed and then performs the calculation.
		 */

		// The q variable is used to track bracket layers.
		// r is used to track the location of brackets for use in parsing the
		// string.
		// j is used just to store s.length() for efficiency purposes.
		int q = 0;
		int r = 0;
		int j = s.length();

		// First calculate the values of brackets.
		for (int i = 0; i < j; i++) {
			if (s.charAt(i) == '(') {

				q++;
				if (q == 1) {

					r = i;

				}

			}
			if (s.charAt(i) == ')') {
				q--;
				if (q == 0) {

					// Changes s to replace brackets with their values after
					// calculator.
					s = s.substring(0, r) + Calculate(s.substring(r + 1, i))
							+ s.substring(i + 1);
					i = 0;
					j = s.length();

				}

			}

			if (i == (j - 1)) {
				if (q != 0) {
					System.out
							.println("Error: Not all brackets have been closed.");
				}
			}

		}

		// Exponential Constant
		j = s.length();
		for (int i = 0; i < j; i++) {

			String c = new String();

			if (s.charAt(i) == 'x' && s.charAt(i + 1) == '@') {

				c = String.valueOf(Math.E);

				s = s.substring(0, i) + c + s.substring(i + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Pi Constant
		j = s.length();
		for (int i = 0; i < j; i++) {

			String c = new String();

			if (s.charAt(i) == 'p' && s.charAt(i + 1) == '@') {

				c = String.valueOf(Math.PI);

				s = s.substring(0, i) + c + s.substring(i + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Logarithm Function

		j = s.length();
		for (int i = 0; i < j; i++) {

			String b = new String();
			String c = new String();

			if (s.charAt(i) == 'l' && s.charAt(i + 1) == '@') {

				b = creepRight(s, i);
				c = String.valueOf(Math.log((Double.valueOf(b))));

				s = s.substring(0, i) + c + s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Sine Function working in radians.
		j = s.length();
		for (int i = 0; i < j; i++) {

			String b = new String();
			String c = new String();

			if (s.charAt(i) == 's' && s.charAt(i + 1) == '@') {

				b = creepRight(s, i);
				c = String.valueOf(Math.sin(Double.valueOf(b)));

				s = s.substring(0, i) + c + s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Cosine Function working in radians.
		j = s.length();
		for (int i = 0; i < j; i++) {

			String b = new String();
			String c = new String();

			if (s.charAt(i) == 'c' && s.charAt(i + 1) == '@') {

				b = creepRight(s, i);
				c = String.valueOf(Math.cos(Double.valueOf(b)));

				s = s.substring(0, i) + c + s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Tangent Function working in radians.
		j = s.length();
		for (int i = 0; i < j; i++) {

			String b = new String();
			String c = new String();

			if (s.charAt(i) == 't' && s.charAt(i + 1) == '@') {

				b = creepRight(s, i);
				c = String.valueOf(Math.tan(Double.valueOf(b)));

				s = s.substring(0, i) + c + s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Square Root Function
		j = s.length();
		for (int i = 0; i < j; i++) {

			String b = new String();
			String c = new String();

			if (s.charAt(i) == 'r' && s.charAt(i + 1) == '@') {

				b = creepRight(s, i);
				c = String.valueOf(Math.sqrt(Double.valueOf(b)));

				s = s.substring(0, i) + c + s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Power Function
		j = s.length();
		for (int i = 0; i < j; i++) {

			String a = new String();
			String b = new String();
			String c = new String();

			if (s.charAt(i) == '^' && s.charAt(i + 1) == '@') {

				a = creepLeft(s, i);
				b = creepRight(s, i);
				c = String.valueOf(Math.pow(Double.valueOf(a),
						Double.valueOf(b)));

				System.out.println(a + " to the power of " + b + " is = " + c);

				s = s.substring(0, i - a.length()) + c
						+ s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();

			}
		}

		// Below this point is the logic for standard addition, subtraction,
		// multiplication and division operations.

		// This function looks for division operators and then performs the
		// calculations.
		j = s.length();
		for (int i = 0; i < j; i++) {
			// Find the number to the left and to the right and then divide
			// them.
			if (s.charAt(i) == '/' && s.charAt(i + 1) == '@') {

				String a = new String();
				String b = new String();
				String c = new String();

				a = creepLeft(s, i);
				b = creepRight(s, i);
				c = String.valueOf((Float.valueOf(a) / Float.valueOf(b)));

				System.out.println(a + " divided by " + b + " is = " + c);

				s = s.substring(0, i - a.length()) + c
						+ s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();
			}
		}

		// This function looks for multiplication operators and then performs
		// the calculation.
		j = s.length();
		for (int i = 0; i < j; i++) {
			// Find the number to the left and to the right and then divide
			// them.
			if (s.charAt(i) == '*' && s.charAt(i + 1) == '@') {

				String a = new String();
				String b = new String();
				String c = new String();

				a = creepLeft(s, i);
				b = creepRight(s, i);
				c = String.valueOf((Float.valueOf(a) * Float.valueOf(b)));

				System.out.println(a + " multiplied by " + b + " is = " + c);

				s = s.substring(0, i - a.length()) + c
						+ s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();
			}
		}

		// This function looks for addition operators and then performs the
		// calculation.
		j = s.length();
		for (int i = 0; i < j; i++) {
			// Find the number to the left and to the right and then divide
			// them.
			if (s.charAt(i) == '+' && s.charAt(i + 1) == '@') {

				String a = new String();
				String b = new String();
				String c = new String();

				a = creepLeft(s, i);
				b = creepRight(s, i);
				c = String.valueOf((Float.valueOf(a) + Float.valueOf(b)));

				System.out.println(a + " add " + b + " is = " + c);

				s = s.substring(0, i - a.length()) + c
						+ s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();
			}
		}

		// This function looks for subtraction operators and then performs the
		// calculation.
		for (int i = 0; i < j; i++) {
			// Find the number to the left and to the right and then divide
			// them.
			if (s.charAt(i) == '-' && s.charAt(i + 1) == '@') {

				String a = new String();
				String b = new String();
				String c = new String();

				a = creepLeft(s, i);
				b = creepRight(s, i);
				c = String.valueOf((Float.valueOf(a) - Float.valueOf(b)));

				System.out.println(a + " minus " + b + " is = " + c);

				s = s.substring(0, i - a.length()) + c
						+ s.substring(i + b.length() + 2);

				System.out.println("The new s is " + s);
				i = 0;
				j = s.length();
			}
		}

		return s;

	}

	// This function is given a string and a starting point.
	// It then looks left and returns the first number it finds.
	// Assumes operators are of +@ format.
	// Start point is the location of operator.
	public String creepLeft(String s, int startPoint) {

		String tempString = new String();

		for (int i = (startPoint - 1); i >= 0; i--) {

			if (isNumberLike(s.charAt(i))) {
				// if the character is a number add it to the front of the
				// temporary string.

				tempString = Character.toString(s.charAt(i)) + tempString;
			}
			// Returns when we find a non numerical character
			else {
				break;
			}

		}
		return tempString;
	}

	// This function is given a string and a starting point.
	// It then looks right and gives the first number it finds.
	// Assumes operators are of +@ format.
	// Start point is the location of operator.
	public String creepRight(String s, int startPoint) {

		String tempString = new String();

		for (int i = startPoint + 2; i < s.length(); i++) {

			if (isNumberLike(s.charAt(i))) {
				// if the character is a number add it to the back of the
				// temporary string.
				if (i == startPoint + 2
						|| (s.charAt(i) != '-' && s.charAt(i) != '+')) {
					tempString += Character.toString(s.charAt(i));
				}
			} else {
				break;
			}

		}
		return tempString;
	}

	// Checks to see if the character is a number like character.
	// Includes . e E - and + for exponential and floating point numbers
	public Boolean isNumberLike(Character c) {

		if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
				|| c == '6' || c == '7' || c == '8' || c == '9' || c == '0'
				|| c == '.' || c == 'e' || c == 'E' || c == '-' || c == '+') {

			return true;
		} else {
			return false;
		}

	}

	// This function checks if a click falls within the rectangle of a button
	// and if so presses that button

	public void buttonMapper(Point p) {

		// Numbers
		// 1
		if (isInRectangle(p, 33, 67, 421, 444)) {
			exp += "1";
			screenUpdate(exp);
		}
		// 2
		if (isInRectangle(p, 73, 106, 421, 444)) {
			exp += "2";
			screenUpdate(exp);
		}
		// 3
		if (isInRectangle(p, 114, 146, 421, 444)) {
			exp += "3";
			screenUpdate(exp);
		}
		// 4
		if (isInRectangle(p, 33, 67, 384, 409)) {
			exp += "4";
			screenUpdate(exp);
		}
		// 5
		if (isInRectangle(p, 73, 107, 385, 409)) {
			exp += "5";
			screenUpdate(exp);
		}
		// 6
		if (isInRectangle(p, 113, 146, 385, 408)) {
			exp += "6";
			screenUpdate(exp);
		}
		// 7
		if (isInRectangle(p, 33, 66, 350, 373)) {
			exp += "7";
			screenUpdate(exp);
		}
		// 8
		if (isInRectangle(p, 73, 107, 350, 373)) {
			exp += "8";
			screenUpdate(exp);
		}
		// 9
		if (isInRectangle(p, 113, 146, 350, 373)) {
			exp += "9";
			screenUpdate(exp);
		}
		// 0
		if (isInRectangle(p, 35, 66, 456, 478)) {
			exp += "0";
			screenUpdate(exp);
		}

		// Utility
		// Decimal Point
		if (isInRectangle(p, 73, 106, 456, 478)) {
			exp += ".";
			screenUpdate(exp);
		}
		// Equals

		if (isInRectangle(p, 193, 226, 454, 478)) {

			equalsCommand();

		}

		// Delete Character
		if (isInRectangle(p, 154, 186, 349, 372)) {
			deleteCharacter();
		}
		// Open Bracket
		if (isInRectangle(p, 99, 127, 319, 335)) {
			exp += "(";
			screenUpdate(exp);
		}
		// Close Bracket
		if (isInRectangle(p, 132, 159, 318, 336)) {
			exp += ")";
			screenUpdate(exp);
		}
		// Negative Number
		if (isInRectangle(p, 32, 59, 290, 308)) {
			exp += "-";
			screenUpdate(exp);
		}

		// Operations
		// TODO ADD FUNCTIONALITY FOR E AND PI
		// Addition
		if (isInRectangle(p, 154, 186, 420, 444)) {
			exp += "+@";
			screenUpdate(exp);
		}
		// Multiplication
		if (isInRectangle(p, 154, 186, 384, 409)) {
			exp += "*@";
			screenUpdate(exp);
		}
		// Division
		if (isInRectangle(p, 192, 226, 384, 409)) {
			exp += "/@";
			screenUpdate(exp);
		}
		// Subtraction
		if (isInRectangle(p, 192, 226, 420, 444)) {
			exp += "-@";
			screenUpdate(exp);
		}
		// Sine
		if (isInRectangle(p, 132, 159, 290, 306)) {
			exp += "s@";
			screenUpdate(exp);
		}
		// Cosine
		if (isInRectangle(p, 167, 193, 290, 306)) {
			exp += "c@";
			screenUpdate(exp);
		}
		// Tangent
		if (isInRectangle(p, 199, 226, 290, 306)) {
			exp += "t@";
			screenUpdate(exp);
		}
		// Logarithm
		if (isInRectangle(p, 199, 226, 261, 277)) {
			exp += "l@";
			screenUpdate(exp);
		}
		// Root
		if (isInRectangle(p, 65, 90, 260, 278)) {
			exp += "r@";
			screenUpdate(exp);
		}
		// Power
		if (isInRectangle(p, 132, 159, 261, 278)) {
			exp += "^@";
			screenUpdate(exp);
		}

	}

	// This function allows for users to type the expression rather then needing
	// to click on the GUI.

	public void keyMapper(KeyEvent key) {
		if (key.getModifiers() == 0) {
			if (key.getKeyCode() == KeyEvent.VK_0
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD0) {
				exp += "0";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_1
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				exp += "1";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_2
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				exp += "2";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_3
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				exp += "3";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_4
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD4) {
				exp += "4";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_5
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD5) {
				exp += "5";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_6
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD6) {
				exp += "6";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_7
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD7) {
				exp += "7";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_8
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD8) {
				exp += "8";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_9
					|| key.getKeyCode() == KeyEvent.VK_NUMPAD9) {
				exp += "9";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_ADD) {
				exp += "+@";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_SUBTRACT) {
				exp += "-@";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_MULTIPLY) {
				exp += "*@";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_DIVIDE
					|| key.getKeyCode() == KeyEvent.VK_SLASH) {
				exp += "/@";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				deleteCharacter();
			}
			if (key.getKeyCode() == KeyEvent.VK_DECIMAL
					|| key.getKeyCode() == KeyEvent.VK_PERIOD) {
				exp += ".";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_EQUALS
					|| key.getKeyCode() == KeyEvent.VK_ENTER) {
				equalsCommand();
			}

		}// if shift is held down
		else if (key.getModifiers() == 1) {
			if (key.getKeyCode() == KeyEvent.VK_9) {
				exp += "(";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_0) {
				exp += ")";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_EQUALS) {
				exp += "+@";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_8) {
				exp += "*@";
				screenUpdate(exp);
			}
			if (key.getKeyCode() == KeyEvent.VK_6) {
				exp += "^@";
				screenUpdate(exp);
			}
		}
	}

	// This function checks whether a specified point is within a specific
	// rectangle.
	// It is intended to be used to verify where on the calculator a user has
	// clicked.
	/**
	 * @param p
	 *            is the point we are checking
	 * @param x1
	 *            is the lower x bound
	 * @param x2
	 *            is the upper x bound
	 * @param y1
	 *            is the upper y bound
	 * @param y2
	 *            is the lower y bound (at the bottom of the screen, higher y
	 *            value)
	 * @return true or false
	 */
	public Boolean isInRectangle(Point p, Integer x1, Integer x2, Integer y1,
			Integer y2) {

		Integer x = (int) p.getX();
		Integer y = (int) p.getY();

		if (x >= x1 && x <= x2) {
			if (y >= y1 && y <= y2) {
				return true;
			}
		}
		return false;
	}

	// This function is used to remove the last entry into both the screenString
	// and the exp string.
	public void deleteCharacter() {
		if (exp.length() > 0) {
			if (exp.charAt(exp.length() - 1) == '@') {
				exp = exp.substring(0, exp.length() - 2);
				screenUpdate(exp);
			} else {
				exp = exp.substring(0, exp.length() - 1);
				screenUpdate(exp);
			}
		}
	}

	// This function is called to perform the calculation required.
	public void equalsCommand() {
		System.out.println("The calculation we are about to perform is " + exp);
		exp = Calculate(exp);
		sText.setText(exp);

		prevAns = sText.getText();

		System.out.println(prevAns);

		calcDone = true;
	}

}

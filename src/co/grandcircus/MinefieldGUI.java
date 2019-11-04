package co.grandcircus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class MinefieldGUI extends JFrame implements ActionListener {

	public static void main(String[] args) {
		MinefieldGUI minefieldGUI = new MinefieldGUI();
		minefieldGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		minefieldGUI.setVisible(true);
	}

	private static final long serialVersionUID = 1L;
	private JLabel minefieldDifficulty;
	private JRadioButton easy;
	private JRadioButton medium;
	private JRadioButton hard;
	private JRadioButton custom;
	private Box difficultyBox;
	private ButtonGroup group;
	// private JFrame frame;

	MinefieldGUI() {
		GridBagConstraints layoutConst = null;
		// frame = new JFrame();
		setTitle("MINESWEEPER");

		minefieldDifficulty = new JLabel();
		minefieldDifficulty.setText("PICK YOUR DIFFICULTY:");

		easy = new JRadioButton("EASY");
		easy.setActionCommand("easy");
		easy.addActionListener(this);

		medium = new JRadioButton("MEDIUM");
		medium.setActionCommand("medium");
		medium.addActionListener(this);

		hard = new JRadioButton("HARD");
		hard.setActionCommand("hard");
		hard.addActionListener(this);

		custom = new JRadioButton("CUSTOM");
		custom.setActionCommand("custom");
		custom.addActionListener(this);

		group = new ButtonGroup();
		group.add(easy);
		group.add(medium);
		group.add(hard);
		group.add(custom);

		difficultyBox = Box.createVerticalBox();
		difficultyBox.add(easy);
		difficultyBox.add(medium);
		difficultyBox.add(hard);
		difficultyBox.add(custom);

		setLayout(new GridBagLayout());

		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		layoutConst.insets = new Insets(10, 10, 10, 10);
		add(minefieldDifficulty, layoutConst);

		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		layoutConst.insets = new Insets(10, 10, 10, 10);
		add(difficultyBox, layoutConst);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Declare variables
		Minefield minefield;
		// HashMaps will store components added to JFrame to be referenced later
		HashMap<String, JButton> jButtonMap = new HashMap<>();
		HashMap<String, JLabel> jLabelMap = new HashMap<>();
		String command = e.getActionCommand();

		// Switch to generate a minefield based on user's difficulty entry
		switch (command) {
		case "easy":
		case "medium":
		case "hard":
			minefield = generateMinefield(command);
			addJButtons(minefield, jButtonMap, jLabelMap); // Method populates JFrame with minefield
			// Remove initial welcome menu and refresh JFrame
			remove(difficultyBox);
			remove(minefieldDifficulty);
			revalidate();
			pack();
			break;
		case "custom":
			// Remove initial welcome menu
			remove(difficultyBox);
			remove(minefieldDifficulty);

			// Generate a minefield with user inputs for width, height, and number of mines
			// gathered from Input Dialog boxes
			minefield = new Minefield(Integer.parseInt(JOptionPane.showInputDialog("Enter width")),
					Integer.parseInt(JOptionPane.showInputDialog("Enter height")),
					Integer.parseInt(JOptionPane.showInputDialog("Enter mines")));
			addJButtons(minefield, jButtonMap, jLabelMap); // Populate JFrame with minefield
			revalidate(); // Refresh JFrame and adjust dimensions to match minefield
			pack();

			break;
		}
	}

	// Method creates a minefield based on user difficulty selection
	public static Minefield generateMinefield(String difficulty) {
		Minefield minefield = null;
		switch (difficulty) {
		case "easy":
			minefield = new Minefield(9, 9, 10);
			break;
		case "medium":
			minefield = new Minefield(16, 16, 40);
			break;
		case "hard":
			minefield = new Minefield(30, 16, 99);
			break;
		}
		return minefield;
	}

	// Method populates the JFrame with components corresponding to the generated
	// minefield and defines logic for the game
	public void addJButtons(Minefield minefield, HashMap<String, JButton> jButtonMap,
			HashMap<String, JLabel> jLabelMap) {
		// Record the game start time
		long start = System.currentTimeMillis();

		Clip clip = null;
		try {
			String fileName = "harvey house 2.wav";
			Path path = Paths.get("src", "co", "grandcircus", fileName);
			File file = path.toFile();
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

		// For loop creates JButtons corresponding to each square in the minefield and
		// appends them to the JFrame
		int counter = 1;
		for (int i = 0; i < minefield.getHeight(); i++) {
			for (int j = 0; j < minefield.getWidth(); j++) {
				int iVal = i;
				int jVal = j;
				Clip stopClip = clip;
				String name = "square" + Integer.toString(counter);
				String display = " ";
				JButton square = new JButton(new AbstractAction(display) {
					private static final long serialVersionUID = 1L;

					// This method defines action on button click
					@Override
					public void actionPerformed(ActionEvent e) {
						remove(jButtonMap.get(name)); // remove the button from the JFrame

						// Create a new JLabel with the same name as the deleted button that displays
						// the corresponding minefield square
						JLabel square = new JLabel(minefield.getMinefield()[iVal][jVal], SwingConstants.CENTER);

						// Define action if the user clicked on a mine
						if (square.getText().equals("*")) {
							// Stop soundtrack loop
							stopClip.stop();

							// Create new clip of explosion noise and play it
							Clip clip;
							try {
								String fileName = "explosion.wav";
								Path path = Paths.get("src", "co", "grandcircus", fileName);
								File file = path.toFile();
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
								// Get a sound clip resource.
								clip = AudioSystem.getClip();
								// Open audio clip and load samples from the audio input stream.
								clip.open(audioIn);
								clip.start();
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							}

							// Clear and refresh the JFrame
							getContentPane().removeAll();
							revalidate();

							// Create new GridBag layout
							GridBagConstraints layoutConst = new GridBagConstraints();

							// For loop repopulates the JFrame with JLabels displaying the revealed
							// minefield
							for (int i = 2; i < minefield.getMinefield().length; i++) {
								for (int j = 0; j < minefield.getMinefield()[0].length; j++) {
									JLabel clearLabel = new JLabel("", SwingConstants.CENTER);
									if (minefield.getMinefield()[i - 2][j].equals("0")) {
										clearLabel.setText("");
									} else {
										clearLabel.setText(minefield.getMinefield()[i - 2][j]);
									}
									if (clearLabel.getText().equals("*")) {
										clearLabel.setForeground(Color.black);
									} else if (clearLabel.getText().equals("1")) {
										clearLabel.setForeground(Color.red);
									} else if (clearLabel.getText().equals("2")) {
										clearLabel.setForeground(Color.blue);
									} else if (clearLabel.getText().equals("3")) {
										clearLabel.setForeground(Color.MAGENTA);
									} else if (clearLabel.getText().equals("4")) {
										clearLabel.setForeground(Color.CYAN);
									} else if (clearLabel.getText().equals("5")) {
										clearLabel.setForeground(Color.PINK);
									}

									layoutConst.gridx = j;
									layoutConst.gridy = i;
									Color myGrey = new Color(200, 200, 200);
									clearLabel.setBackground(myGrey);
									clearLabel.setOpaque(true);
									clearLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
									clearLabel.setPreferredSize(new Dimension(20, 20));
									add(clearLabel, layoutConst);
								}
							}
							pack();
							revalidate();

							// Create a new JLabel to let the user know they lost and add it to the JFrame
							JLabel youLose = new JLabel("YOU LOSE!");
							layoutConst.gridx = 0;
							layoutConst.gridy = 0;
							layoutConst.gridwidth = minefield.getWidth();
							add(youLose, layoutConst);

							// Gather the end time for the game and calculate the total time elapsed since
							// the game started
							long finish = System.currentTimeMillis();
							long timeElapsed = (finish - start) / 1000;

							// Create a new JLabel to display total play time and add it to the JFrame
							JLabel timer = new JLabel("TIME ELAPSED: " + timeElapsed);
							layoutConst = new GridBagConstraints();
							layoutConst.gridx = 0;
							layoutConst.gridy = 1;
							layoutConst.gridwidth = minefield.getWidth();
							add(timer, layoutConst);

							// Refresh and resize the JFrame to match new elements
							pack();
							revalidate();

						} else { // If the user did not click a bomb

							// If the square was not adjacent to a bomb, set text to an empty string
							if (square.getText().equals("0")) {
								square.setText("");
							}
							// Otherwise set text color according to the number of adjacent mines
							else if (square.getText().equals("*")) {
								removeAll();
							} else if (square.getText().equals("1")) {
								square.setForeground(Color.red);
							} else if (square.getText().equals("2")) {
								square.setForeground(Color.blue);
							} else if (square.getText().equals("3")) {
								square.setForeground(Color.MAGENTA);
							} else if (square.getText().equals("4")) {
								square.setForeground(Color.CYAN);
							} else if (square.getText().equals("5")) {
								square.setForeground(Color.PINK);
							}

							// Set reveal square background color and borders
							Color myGrey = new Color(200, 200, 200);
							square.setBackground(myGrey);
							square.setOpaque(true);
							square.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
							square.setPreferredSize(new Dimension(20, 20));
							square.setName(name);
							jLabelMap.put(name, square); // Add revealed square to JLabel HashMap to reference later

							// Create new GridBag constraints
							GridBagConstraints layoutConst = new GridBagConstraints();
							layoutConst.gridx = jVal;
							layoutConst.gridy = iVal;
							add(square, layoutConst); // Add revealed square to JFrame
							revalidate();

							// If the revealed square was a zero, call the method to reveal adjacent squares
							if (minefield.getMinefield()[iVal][jVal].equals("0")) {
								checkAdjacentSquares(jButtonMap, jLabelMap, minefield, iVal, jVal, name);
							}
						}
					}
				});

				// Set revealed square background color
				square.setBackground(Color.DARK_GRAY);
				counter++;
				Border borderUse = square.getBorder(); // Set revealed square border to match covered square border

				// Add right mouse click functionality to button
				square.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// If the user right-clicks, set the covered square to a flag icon or clear any
						// icon that's already there
						if (e.getButton() == MouseEvent.BUTTON3) {
							try {

								if (square.getIcon() == null) {
									Image img = ImageIO.read(getClass().getResource("flag.png"));
									square.setIcon(new ImageIcon(img));
									square.setIconTextGap(0);
									square.setContentAreaFilled(false);
									square.setBorder(borderUse);
								} else {
									square.setIcon(null);
								}
							} catch (IOException e1) {
							}
							// Refresh and resize JFrame
							pack();
							revalidate();
							int counter = 0;
							String nextName = "square1";
							for (int i = 0; i < minefield.getHeight(); i++) {
								for (int j = 0; j < minefield.getWidth(); j++) {
									
									if (minefield.getMinefield()[i][j].equals("*")
											&& jButtonMap.get(nextName).getIcon() != null) {
										counter++;
									}
									
									/*if (!minefield.getMinefield()[i][j].equals("*")
											&& jLabelMap.containsKey(nextName)) {
										counter++;
									}*/
									nextName = nextName.substring(0, 6) + Integer
											.toString(Integer.parseInt(nextName.substring(6, nextName.length())) + 1);
								}
							}

							// If the counter equals the total number of non-mine squares in the minefield,
							// the user has won
							if (counter == minefield.getNumBombs()) {
							//if (counter == (minefield.getHeight() * minefield.getWidth() - minefield.getNumBombs())) {
								// Stop soundtrack loop
								stopClip.stop();

								// Create new clip of victory music and loop it
								Clip clip;
								try {
									String fileName = "cool_disco .wav";
									Path path = Paths.get("src", "co", "grandcircus", fileName);
									File file = path.toFile();
									AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
									// Get a sound clip resource.
									clip = AudioSystem.getClip();
									// Open audio clip and load samples from the audio input stream.
									clip.open(audioIn);
									clip.loop(Clip.LOOP_CONTINUOUSLY);
								} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
								}

								getContentPane().removeAll(); // Clear the JFrame

								// Create and add new JLabel letting user know they won
								JLabel youWin = new JLabel("YOU WIN!");
								GridBagConstraints layoutConst = new GridBagConstraints();
								layoutConst.gridx = 0;
								layoutConst.gridy = 0;
								add(youWin, layoutConst);

								// Gather game end time and calculate total time spent
								long finish = System.currentTimeMillis();
								long timeElapsed = (finish - start) / 1000;

								// Create new JLabel to show total time spent playing and add to Jframe
								JLabel timer = new JLabel("TIME ELAPSED: " + timeElapsed);
								layoutConst = new GridBagConstraints();
								layoutConst.gridx = 0;
								layoutConst.gridy = 1;
								add(timer, layoutConst);

								revalidate();// Refresh JFrame
							}
						}
					}
				});

				// Format created JButtons
				square.setPreferredSize(new Dimension(20, 20));
				jButtonMap.put(name, square); // Add the button the HashMap to reference later
				GridBagConstraints layoutConst = new GridBagConstraints();
				layoutConst.gridx = j;
				layoutConst.gridy = i;
				add(square, layoutConst); // Add button to JFrame
			}
		}
	}

	// Method reveals adjacent squares if the user clicked on an empty square
	public void checkAdjacentSquares(HashMap<String, JButton> jButtonMap, HashMap<String, JLabel> jLabelMap,
			Minefield minefield, int iVal, int jVal, String name) {

		// Declare strings that will correspond to HashMap keys for adjacent squares
		String nextName = name;
		String nextName1 = name;
		String nextName2 = name;
		String nextName3 = name;
		String nextName4 = name;
		String nextName5 = name;
		String nextName6 = name;
		String nextName7 = name;

		// Reveal all adjacent squares if they match an index location in the minefield
		try {
			if (!minefield.getMinefield()[iVal + 1][jVal].equals("*")) {
				nextName = nextName.substring(0, 6) + Integer
						.toString(Integer.parseInt(nextName.substring(6, nextName.length())) + minefield.getWidth());
				if (!jLabelMap.containsKey(nextName)) {
					jButtonMap.get(nextName).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal + 1][jVal - 1].equals("*")) {
				nextName1 = nextName1.substring(0, 6) + Integer.toString(
						Integer.parseInt(nextName1.substring(6, nextName1.length())) + minefield.getWidth() - 1);
				if (!jLabelMap.containsKey(nextName1)) {
					jButtonMap.get(nextName1).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal + 1][jVal + 1].equals("*")) {
				nextName2 = nextName2.substring(0, 6) + Integer.toString(
						Integer.parseInt(nextName2.substring(6, nextName2.length())) + minefield.getWidth() + 1);

				if (!jLabelMap.containsKey(nextName2)) {
					jButtonMap.get(nextName2).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal - 1][jVal].equals("*")) {
				nextName3 = nextName3.substring(0, 6) + Integer
						.toString(Integer.parseInt(nextName3.substring(6, nextName3.length())) - minefield.getWidth());

				if (!jLabelMap.containsKey(nextName3)) {
					jButtonMap.get(nextName3).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal - 1][jVal - 1].equals("*")) {
				nextName4 = nextName4.substring(0, 6) + Integer.toString(
						Integer.parseInt(nextName4.substring(6, nextName4.length())) - minefield.getWidth() - 1);
				if (!jLabelMap.containsKey(nextName4)) {
					jButtonMap.get(nextName4).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal - 1][jVal + 1].equals("*")) {
				nextName5 = nextName5.substring(0, 6) + Integer.toString(
						Integer.parseInt(nextName5.substring(6, nextName5.length())) - minefield.getWidth() + 1);
				if (!jLabelMap.containsKey(nextName5)) {
					jButtonMap.get(nextName5).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal][jVal + 1].equals("*")) {
				nextName6 = nextName6.substring(0, 6)
						+ Integer.toString(Integer.parseInt(nextName6.substring(6, nextName6.length())) + 1);
				if (!jLabelMap.containsKey(nextName6)) {
					jButtonMap.get(nextName6).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal][jVal - 1].equals("*")) {
				nextName7 = nextName7.substring(0, 6)
						+ Integer.toString(Integer.parseInt(nextName7.substring(6, nextName7.length())) - 1);
				if (!jLabelMap.containsKey(nextName7)) {
					jButtonMap.get(nextName7).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
	}
}

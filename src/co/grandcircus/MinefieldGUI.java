package co.grandcircus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class MinefieldGUI extends JFrame implements ActionListener {

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
		Minefield minefield;
		HashMap<String, JButton> jButtonMap = new HashMap<>();
		HashMap<String, JLabel> jLabelMap = new HashMap<>();
		String command = e.getActionCommand();
		switch (command) {
		case "easy":
		case "medium":
		case "hard":
			minefield = generateMinefield(command);
			addJButtons(minefield, jButtonMap, jLabelMap);
			remove(difficultyBox);
			remove(minefieldDifficulty);
			revalidate();
			pack();
			break;
		case "custom":
			remove(difficultyBox);
			remove(minefieldDifficulty);
			minefield = new Minefield(Integer.parseInt(JOptionPane.showInputDialog("Enter width")),
					Integer.parseInt(JOptionPane.showInputDialog("Enter height")),
					Integer.parseInt(JOptionPane.showInputDialog("Enter mines")));
			addJButtons(minefield, jButtonMap, jLabelMap);
			revalidate();
			pack();

			break;
		}
	}

	public static void main(String[] args) {
		MinefieldGUI minefieldGUI = new MinefieldGUI();
		minefieldGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		minefieldGUI.setVisible(true);
	}

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

	public void addJButtons(Minefield minefield, HashMap<String, JButton> jButtonMap,
			HashMap<String, JLabel> jLabelMap) {
		long start = System.currentTimeMillis();
		int counter = 1;
		for (int i = 0; i < minefield.getHeight(); i++) {
			for (int j = 0; j < minefield.getWidth(); j++) {
				int iVal = i;
				int jVal = j;
				String name = "square" + Integer.toString(counter);
				String display = " ";
				JButton square = new JButton(new AbstractAction(display) {
					private static final long serialVersionUID = 1L;

					/**
					 *
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						remove(jButtonMap.get(name));
						JLabel square = new JLabel(minefield.getMinefield()[iVal][jVal], SwingConstants.CENTER);
						if (square.getText().equals("*")) {
							getContentPane().removeAll();
							GridBagConstraints layoutConst = new GridBagConstraints();
							layoutConst.gridx = 0;
							layoutConst.gridy = 0;
							JLabel youLose = new JLabel("YOU LOSE!");
							add(youLose, layoutConst);
							
							long finish = System.currentTimeMillis();
							long timeElapsed = (finish - start) / 1000;
							layoutConst = new GridBagConstraints();
							layoutConst.gridx = 0;
							layoutConst.gridy = 1;
							JLabel timer = new JLabel("TIME ELAPSED: " + timeElapsed);
							add(timer,layoutConst);
							revalidate();

						} else {
							if (square.getText().equals("0")) {
								square.setText("");
							} else if (square.getText().equals("*")) {
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
							Color myGrey = new Color(200, 200, 200);
							square.setBackground(myGrey);
							square.setOpaque(true);
							square.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
							square.setPreferredSize(new Dimension(20, 20));
							square.setName(name);
							jLabelMap.put(name, square);
							GridBagConstraints layoutConst = new GridBagConstraints();
							layoutConst.gridx = jVal;
							layoutConst.gridy = iVal;
							add(square, layoutConst);
							revalidate();
							if (minefield.getMinefield()[iVal][jVal].equals("0")) {
								checkAdjacentSquares(jButtonMap, jLabelMap, minefield, iVal, jVal, name);
							}
							int counter = 0;
							String nextName = "square1";
							for (int i = 0; i < minefield.getHeight(); i++) {
								for (int j = 0; j < minefield.getWidth(); j++) {
									if (!minefield.getMinefield()[i][j].equals("*")
											&& jLabelMap.containsKey(nextName)) {
										counter++;
									}
									if (nextName.length() == 7) {
										nextName = nextName.substring(0, 6)
												+ Integer.toString(Integer.parseInt(nextName.substring(6, 7)) + 1);
									} else if (nextName.length() == 8) {
										nextName = nextName.substring(0, 6)
												+ Integer.toString(Integer.parseInt(nextName.substring(6, 8)) + 1);
									} else if (nextName.length() == 9) {
										nextName = nextName.substring(0, 6)
												+ Integer.toString(Integer.parseInt(nextName.substring(6, 9)) + 1);
									}	
								}
							}
							System.out.println("TOTAL:" + counter);
							System.out.println("TOTAL TO HIT: " + Integer.toString(minefield.getHeight() * minefield.getWidth() - minefield.getNumBombs()));
							if (counter == (minefield.getHeight() * minefield.getWidth() - minefield.getNumBombs())) {
								getContentPane().removeAll();
								layoutConst = new GridBagConstraints();
								layoutConst.gridx = 0;
								layoutConst.gridy = 0;
								JLabel youWin = new JLabel("YOU WIN!");
								add(youWin, layoutConst);
								
								long finish = System.currentTimeMillis();
								long timeElapsed = (finish - start) / 1000;
								layoutConst = new GridBagConstraints();
								layoutConst.gridx = 0;
								layoutConst.gridy = 1;
								JLabel timer = new JLabel("TIME ELAPSED: " + timeElapsed);
								add(timer, layoutConst);
								revalidate();
							}
						}
					}
				});
				square.setBackground(Color.DARK_GRAY);
				counter++;
				Border borderUse = square.getBorder();
				square.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == MouseEvent.BUTTON3) {
							try {
								Image img = ImageIO.read(getClass().getResource("flag.png"));
								square.setIcon(new ImageIcon(img));
								square.setIconTextGap(0);
								square.setContentAreaFilled(false);
								square.setBorder(borderUse);
							} catch (IOException e1) {
							}
							pack();
							revalidate();
						}
					}
				});

				square.setPreferredSize(new Dimension(20, 20));
				jButtonMap.put(name, square);
				GridBagConstraints layoutConst = new GridBagConstraints();
				layoutConst.gridx = j;
				layoutConst.gridy = i;
				add(square, layoutConst);
			}
		}
	}

	public void uncoverSquare(Minefield minefield, int i, int j) {
		if (!minefield.getMinefield()[i][j].equals("*")) {
			MineText.writeInput(minefield, j + 1, i + 1);
		} else {
			JTextField youLose = new JTextField();
			youLose.setText("BOOM! YOU LOSE.");
			GridBagConstraints layoutConst = new GridBagConstraints();
			layoutConst.gridx = 1;
			layoutConst.gridy = 1;
			layoutConst.insets = new Insets(10, 10, 10, 10);
			add(youLose, layoutConst);
			super.pack();
			revalidate();
		}
	}

	public void checkAdjacentSquares(HashMap<String, JButton> jButtonMap, HashMap<String, JLabel> jLabelMap,
			Minefield minefield, int iVal, int jVal, String name) {
		String nextName = name;
		String nextName1 = name;
		String nextName2 = name;
		String nextName3 = name;
		String nextName4 = name;
		String nextName5 = name;
		String nextName6 = name;
		String nextName7 = name;

		try {
			if (!minefield.getMinefield()[iVal + 1][jVal].equals("*")) {
				if (nextName.length() == 7) {
					nextName = nextName.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName.substring(6, 7)) + minefield.getWidth());
				} else if (nextName.length() == 8) {
					nextName = nextName.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName.substring(6, 8)) + minefield.getWidth());
				} else if (nextName.length() == 9) {
					nextName = nextName.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName.substring(6, 9)) + minefield.getWidth());
				}
				if (!jLabelMap.containsKey(nextName)) {
					jButtonMap.get(nextName).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal + 1][jVal - 1].equals("*")) {
				if (nextName1.length() == 7) {
					nextName1 = nextName1.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName1.substring(6, 7)) + minefield.getWidth() - 1);
				} else if (nextName1.length() == 8) {
					nextName1 = nextName1.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName1.substring(6, 8)) + minefield.getWidth() - 1);
				} else if (nextName1.length() == 9) {
					nextName1 = nextName1.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName1.substring(6, 9)) + minefield.getWidth() - 1);
				}
				if (!jLabelMap.containsKey(nextName1)) {
					jButtonMap.get(nextName1).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal + 1][jVal + 1].equals("*")) {
				if (nextName2.length() == 7) {
					nextName2 = nextName2.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName2.substring(6, 7)) + minefield.getWidth() + 1);
				} else if (nextName2.length() == 8) {
					nextName2 = nextName2.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName2.substring(6, 8)) + minefield.getWidth() + 1);
				} else if (nextName2.length() == 9) {
					nextName2 = nextName2.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName2.substring(6, 9)) + minefield.getWidth() + 1);
				}
				if (!jLabelMap.containsKey(nextName2)) {
					jButtonMap.get(nextName2).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal - 1][jVal].equals("*")) {
				if (nextName3.length() == 7) {
					nextName3 = nextName3.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName3.substring(6, 7)) - minefield.getWidth());
				} else if (nextName1.length() == 8) {
					nextName3 = nextName3.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName3.substring(6, 8)) - minefield.getWidth());
				} else if (nextName1.length() == 9) {
					nextName3 = nextName3.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName3.substring(6, 9)) - minefield.getWidth());
				}
				if (!jLabelMap.containsKey(nextName3)) {
					jButtonMap.get(nextName3).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal - 1][jVal - 1].equals("*")) {
				if (nextName4.length() == 7) {
					nextName4 = nextName4.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName4.substring(6, 7)) - minefield.getWidth() - 1);
				} else if (nextName4.length() == 8) {
					nextName4 = nextName4.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName4.substring(6, 8)) - minefield.getWidth() - 1);
				} else if (nextName4.length() == 9) {
					nextName4 = nextName4.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName4.substring(6, 9)) - minefield.getWidth() - 1);
				}
				if (!jLabelMap.containsKey(nextName4)) {
					jButtonMap.get(nextName4).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal - 1][jVal + 1].equals("*")) {
				if (nextName5.length() == 7) {
					nextName5 = nextName5.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName5.substring(6, 7)) - minefield.getWidth() + 1);
				} else if (nextName5.length() == 8) {
					nextName5 = nextName5.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName5.substring(6, 8)) - minefield.getWidth() + 1);
				} else if (nextName5.length() == 9) {
					nextName5 = nextName5.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName5.substring(6, 9)) - minefield.getWidth() + 1);
				}
				if (!jLabelMap.containsKey(nextName5)) {
					jButtonMap.get(nextName5).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal][jVal + 1].equals("*")) {
				if (nextName6.length() == 7) {
					nextName6 = nextName6.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName6.substring(6, 7)) + 1);
				} else if (nextName6.length() == 8) {
					nextName6 = nextName6.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName6.substring(6, 8)) + 1);
				} else if (nextName6.length() == 9) {
					nextName6 = nextName6.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName6.substring(6, 9)) + 1);
				}
				if (!jLabelMap.containsKey(nextName6)) {
					jButtonMap.get(nextName6).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
		try {
			if (!minefield.getMinefield()[iVal][jVal - 1].equals("*")) {
				if (nextName7.length() == 7) {
					nextName7 = nextName7.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName7.substring(6, 7)) - 1);
				} else if (nextName7.length() == 8) {
					nextName7 = nextName7.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName7.substring(6, 8)) - 1);
				} else if (nextName7.length() == 9) {
					nextName7 = nextName7.substring(0, 6)
							+ Integer.toString(Integer.parseInt(nextName7.substring(6, 9)) - 1);
				}
				if (!jLabelMap.containsKey(nextName7)) {
					jButtonMap.get(nextName7).doClick();
				}
			}
		} catch (IndexOutOfBoundsException | StackOverflowError e) {
		}
	}
}

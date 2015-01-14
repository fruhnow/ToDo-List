package de.fr.todolist;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ToDoList extends JFrame
{
	private DefaultListModel<Entry> lm;
	private JTextField txtInput;
	private JTextField txtCount;

	public ToDoList()
	{
		super();
		this.setSize(500, 300);
		Container con = getContentPane();
		con.setLayout(new BorderLayout());
		this.addWindowListener(new WindowListener()
		{
			@Override
			public void windowClosing(WindowEvent arg0)
			{
				exit();

			}

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});

		// Textfeld-Panel und Textfelder erzeugen und hinzufügen
		JPanel tp = new JPanel();
		tp.setLayout(new GridLayout(1, 2));
		con.add(tp, BorderLayout.NORTH);
		txtInput = new JTextField();
		txtCount = new JTextField();
		tp.add(txtInput, BorderLayout.WEST);
		tp.add(txtCount, BorderLayout.EAST);

		// Scrollbare Liste erzeugen und hinzufügen
		lm = new DefaultListModel<Entry>();
		JList list = new JList(lm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane listscroll = new JScrollPane(list);
		con.add(listscroll);

		// gespeicherte Daten laden
		loadDataToList();

		// Panel für Buttons erzeugen und dem Container hinzufügen
		JPanel panForButtons = new JPanel();
		panForButtons.setLayout(new GridLayout(4, 0));
		con.add(panForButtons, BorderLayout.EAST);

		// Buttons erzeugen und hinzufügen
		JButton btnAdd = new JButton("add");
		JButton btnRemove = new JButton("remove");
		JButton btnRemoveAll = new JButton("remove All");
		JButton btnExit = new JButton("exit");
		panForButtons.add(btnAdd);
		panForButtons.add(btnRemove);
		panForButtons.add(btnRemoveAll);
		panForButtons.add(btnExit);

		// Funktion von Buttons erzeugen
		btnAdd.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addElement();
			}

		});

		txtInput.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addElement();
			}
		});
		
		txtCount.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				addElement();
			}
		});

		btnRemove.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				lm.remove(list.getSelectedIndex());
			}

		});

		btnRemoveAll.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				lm.removeAllElements();

			}

		});

		btnExit.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				exit();

			}

		});

	}

	private void loadDataToList()
	{
		File f = new File("liste.txt");
		if (f.exists())
		{
			try
			{
				BufferedReader in = new BufferedReader(new InputStreamReader(
						new FileInputStream(f)));
				String line;
				while ((line = in.readLine()) != null)
				{
					String[] splittedLine = line.split(",");
					System.out.println(splittedLine.length - 1);
					if (splittedLine.length == 0)
					{
						return;
					}

					StringBuilder build = new StringBuilder();
					for (int i = 0; i < (splittedLine.length - 1); i++)
					{
						build.append(splittedLine[i]);
						if (i != (splittedLine.length - 2))
						{
							build.append(", ");
						}
					}
					Entry newEntry = new Entry(
							build.toString(),
							Integer.parseInt(splittedLine[splittedLine.length - 1]
									.trim())); // TODO: Exception bei
												// ParseInt-Fail fangen
					lm.addElement(newEntry);
				}
				in.close();
			} catch (IOException e)
			{
				System.err
						.println("Fehler: Datei wurde ggf. verändert oder beschädigt!");
				e.printStackTrace();
			}
		}
	}

	protected void exit()
	{
		try
		{
			File f = new File("liste.txt");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f), "UTF8"));
			for (int i = 0; i < lm.getSize(); i++)
			{
				String line = lm.getElementAt(i).toString();
				out.write(lm.getElementAt(i).toString());
				out.newLine();
			}
			out.close();
		} catch (IOException e)
		{
			System.err.println("Fehler: Speichern fehlgeschlagen!");
			e.printStackTrace();
		}

		this.dispose();

	}

	private void addElement()
	{
		Entry newElement = new Entry(txtInput.getText(),
				Integer.parseInt(txtCount.getText()));
		lm.addElement(newElement);
		txtInput.setText("");
		txtCount.setText("");
		txtInput.requestFocus();
	}

	public static void main(String[] args)
	{
		ToDoList todo = new ToDoList();
		todo.show();
	}

}

package de.fr.todolist;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private DefaultListModel<String> lm;

	public ToDoList()
	{
		super();
		this.setSize(500, 300);
		Container con = getContentPane();
		con.setLayout(new BorderLayout());

		// Textfeld erzeugen und hinzufügen
		JTextField txtInput = new JTextField();
		con.add(txtInput, BorderLayout.NORTH);

		// Scrollbare Liste erzeugen und hinzufügen
		lm = new DefaultListModel<String>();
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
				lm.addElement(txtInput.getText());
				txtInput.setText("");
				txtInput.requestFocus();
			}

		});

		txtInput.addKeyListener(new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent e)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					lm.addElement(txtInput.getText());
					txtInput.setText("");
					txtInput.requestFocus();
				}
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
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//				for (int i = 0; i < in.lines().count(); i++)
				String line;
				while((line = in.readLine()) != null)
				{
					lm.addElement(line);
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
				String line = lm.getElementAt(i);
				out.write(lm.getElementAt(i));
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

	public static void main(String[] args)
	{
		ToDoList todo = new ToDoList();
		todo.show();
	}

}

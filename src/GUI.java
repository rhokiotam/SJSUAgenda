import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLayeredPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.CardLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingUtilities;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.zinternaltools.DemoPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import com.github.lgooddatepicker.zinternaltools.InternalUtilities;
import com.github.lgooddatepicker.zinternaltools.WrapLayout;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.CalendarBorderProperties;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeArea;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.privatejgoodies.forms.factories.CC;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import javax.swing.JTextArea;
import org.json.*;

public class GUI extends JFrame {

	TaskHandler taskHandler = new TaskHandler();
	private JButton removeTaskButton;
	private JPanel contentPane;
	private JTextField addTaskNameTextField;
	private JTextField addCategoryTextField;
	private JTextField editTaskNameTextField;
	private JTextField editCategoryTextField;
	private JLayeredPane mainLayeredPane;
	private JPanel addTaskPane;
	private JPanel editTaskPane;
	private JScrollPane taskScrollPane;
	private TimePicker addStartTimePicker;
	private TimePicker addEndTimePicker;
	private JList<String> taskListVisual;
	private TimePicker editStartTimePicker;
	private TimePicker editEndTimePicker;
	static DatePicker addDatePicker;
	static DatePicker editDatePicker;
	DefaultListModel<String> model = new DefaultListModel<String>();
	
	//DefaultListModel<Task> model = new DefaultListModel<Task>();
	
	public void switchPanels(JLayeredPane pane, JPanel panel)
    {
        pane.removeAll();
        pane.add(panel);
        pane.repaint();
        pane.revalidate();
        
    }
	
	public void switchToTaskPanel(JLayeredPane pane, JScrollPane panel)
    {
        pane.removeAll();
        pane.add(panel);
        pane.repaint();
        pane.revalidate();
        
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 843, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton logoButton = new JButton("SJSUAgenda");
		logoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logoButtonactionPerformed(e);
			}

		});
		logoButton.setBounds(0, 11, 166, 105);
		contentPane.add(logoButton);
		
		JPanel functionButtonPanel = new JPanel();
		functionButtonPanel.setBackground(new Color(0, 0, 128));
		functionButtonPanel.setBounds(0, 168, 166, 351);
		contentPane.add(functionButtonPanel);
		functionButtonPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JButton addTaskPanelSwitch = new JButton("Add Task");
		addTaskPanelSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					addTaskPanelSwitchactionPerfomed(e);
			}
		});
		functionButtonPanel.add(addTaskPanelSwitch);
		
		removeTaskButton = new JButton("Remove Task");
		removeTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeTaskButtonactionPerformed(e);
			}

		});
		functionButtonPanel.add(removeTaskButton);
		
		JButton editTaskPanelSwitch = new JButton("Edit Task");
		editTaskPanelSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editTaskPanelSwitchactionPerformed(e);
			}			
		});
		functionButtonPanel.add(editTaskPanelSwitch);
		
		mainLayeredPane = new JLayeredPane();
		mainLayeredPane.setBounds(176, 168, 641, 351);
		contentPane.add(mainLayeredPane);
		mainLayeredPane.setLayout(new CardLayout(0, 0));
		
		taskScrollPane = new JScrollPane();
		mainLayeredPane.add(taskScrollPane, "name_11863656002600");
		
		taskListVisual = new JList<>();
		taskScrollPane.setViewportView(taskListVisual);
		taskListVisual.setModel(model);
		
		addTaskPane = new JPanel();
		mainLayeredPane.add(addTaskPane, "name_3671592714000");
		addTaskPane.setLayout(null);
		
		JPanel addTaskFunctionButtonPanel = new JPanel();
		addTaskFunctionButtonPanel.setBounds(160, 11, 309, 56);
		addTaskPane.add(addTaskFunctionButtonPanel);
		addTaskFunctionButtonPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonactionPerformed(e);
			}
		});
		addTaskFunctionButtonPanel.add(addButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearButtonactionPerformed(e);
			}
			
		});
		addTaskFunctionButtonPanel.add(clearButton);
		
		JButton addCancelButton = new JButton("Cancel");
		addCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCancelButtonactionPerformed(e);
			}

		});
		addTaskFunctionButtonPanel.add(addCancelButton);
		
		JPanel addTaskInputTextFields = new JPanel();
		addTaskInputTextFields.setBounds(145, 85, 331, 255);
		addTaskPane.add(addTaskInputTextFields);
		addTaskInputTextFields.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel addTaskNameLabel = new JLabel("Task Name");
		addTaskInputTextFields.add(addTaskNameLabel);
		
		addTaskNameTextField = new JTextField();
		addTaskInputTextFields.add(addTaskNameTextField);
		addTaskNameTextField.setColumns(10);
		
		JLabel addStartTimeLabel = new JLabel("Start Time (HH:MM)");
		addTaskInputTextFields.add(addStartTimeLabel);
		
		addStartTimePicker = new TimePicker();
		addStartTimePicker.getComponentToggleTimeMenuButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addTaskInputTextFields.add(addStartTimePicker);
		
		JLabel addEndTimeLabel = new JLabel("End Time (HH:MM)");
		addTaskInputTextFields.add(addEndTimeLabel);
		
		addEndTimePicker = new TimePicker();
		addEndTimePicker.getComponentToggleTimeMenuButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addTaskInputTextFields.add(addEndTimePicker);
		
		JLabel addDateLabel = new JLabel("Date (mm/dd/yy)");
		addTaskInputTextFields.add(addDateLabel);
		
		addDatePicker = new DatePicker();
		addDatePicker.getComponentToggleCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		addTaskInputTextFields.add(addDatePicker);
		
		JLabel addCategoryLabel = new JLabel("Category");
		addTaskInputTextFields.add(addCategoryLabel);
		
		addCategoryTextField = new JTextField();
		addTaskInputTextFields.add(addCategoryTextField);
		addCategoryTextField.setColumns(10);
		
		editTaskPane = new JPanel();
		mainLayeredPane.add(editTaskPane, "name_3671603738700");
		editTaskPane.setLayout(null);
		
		JPanel editTaskFunctionButtonPanel = new JPanel();
		editTaskFunctionButtonPanel.setBounds(186, 11, 250, 68);
		editTaskPane.add(editTaskFunctionButtonPanel);
		editTaskFunctionButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editButtonactionPerformed(e);
			}
		});
		editTaskFunctionButtonPanel.add(editButton);
		
		JButton editCancelButton = new JButton("Cancel");
		editCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCancelButtonactionPerformed(e);
			}

		});
		editTaskFunctionButtonPanel.add(editCancelButton);
		
		JPanel editTaskInputTextFields = new JPanel();
		editTaskInputTextFields.setBounds(145, 85, 331, 255);
		editTaskPane.add(editTaskInputTextFields);
		editTaskInputTextFields.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel editTaskNameLabel = new JLabel("Task Name");
		editTaskInputTextFields.add(editTaskNameLabel);
		
		editTaskNameTextField = new JTextField();
		editTaskInputTextFields.add(editTaskNameTextField);
		editTaskNameTextField.setColumns(10);
		
		JLabel editStartTimeLabel = new JLabel("Start Time (HH:MM)");
		editTaskInputTextFields.add(editStartTimeLabel);
		
		editStartTimePicker = new TimePicker();
		editTaskInputTextFields.add(editStartTimePicker);
		
		JLabel editEndTimeLabel = new JLabel("End Time (HH:MM)");
		editTaskInputTextFields.add(editEndTimeLabel);
		
		editEndTimePicker = new TimePicker();
		editTaskInputTextFields.add(editEndTimePicker);
		
		JLabel editDateLabel = new JLabel("Date (mm/dd/yy)");
		editTaskInputTextFields.add(editDateLabel);
		
		editDatePicker = new DatePicker();
		editTaskInputTextFields.add(editDatePicker);
		
		JLabel editCategoryLabel = new JLabel("Category");
		editTaskInputTextFields.add(editCategoryLabel);
		
		editCategoryTextField = new JTextField();
		editTaskInputTextFields.add(editCategoryTextField);
		editCategoryTextField.setColumns(10);
		
		JPanel currentDatePanelMainMenu = new JPanel();
		currentDatePanelMainMenu.setBounds(383, 11, 185, 105);
		contentPane.add(currentDatePanelMainMenu);
		currentDatePanelMainMenu.setLayout(null);
		
		JLabel dateLabel = new JLabel();
		dateLabel.setBounds(49, 50, 108, 44);
		dateLabel.setText(JPaneDate.gimmieTimeNow());
		currentDatePanelMainMenu.add(dateLabel);
		
		JLabel niceLabel = new JLabel("Today's Date Is:");
		niceLabel.setBounds(49, 11, 124, 58);
		currentDatePanelMainMenu.add(niceLabel);
				
		
		for(int i = 0; i < taskHandler.getTasks().length(); i++) {
			JSONObject temp = taskHandler.getTasks().getJSONObject(i);
			model.addElement(
					"<html><div style='border:2px solid black; margin-right: 0;'><<h1><b>" + temp.getString("name") + "</b></h1><div style='float:left; padding-right:1 rem;'>" + 
					temp.getString("date") + " &nbsp;&nbsp; | &nbsp;&nbsp; " + temp.getString("start") + " - " + temp.getString("end") + "</div><p>" +
					temp.getString("category") + "</p></div></html>"
					);
		}
	}

	private void addTaskPanelSwitchactionPerfomed(ActionEvent e) {
		// TODO Auto-generated method stub
		switchPanels(mainLayeredPane, addTaskPane);
	}
	
	private void editTaskPanelSwitchactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switchPanels(mainLayeredPane, editTaskPane);
		int index = taskListVisual.getSelectedIndex();
		JSONObject toBeEdited = new JSONObject(taskListVisual.getSelectedValue());
		editTaskNameTextField.setText(toBeEdited.getString("name"));
		editDatePicker.setDate(LocalDate.parse(toBeEdited.getString("date"), DateTimeFormatter.ofPattern("MMMM d, yyyy")));
		editStartTimePicker.setText(toBeEdited.getString("start"));
		editEndTimePicker.setText(toBeEdited.getString("end"));
		editCategoryTextField.setText(toBeEdited.getString("category"));
	}
	
	private void addCancelButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switchToTaskPanel(mainLayeredPane, taskScrollPane);
	}
	
	private void addButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String uuid = taskHandler.addTask(addTaskNameTextField.getText(), addDatePicker.getText(), addStartTimePicker.getText(), addEndTimePicker.getText(), addCategoryTextField.getText());
		model.addElement("<html><h1>" + taskHandler.getTask(uuid).toString() + "</h1></html>");
		addTaskNameTextField.setText("");
		addStartTimePicker.clear();
		addEndTimePicker.clear();
		addDatePicker.clear();
		addCategoryTextField.setText("");
		switchToTaskPanel(mainLayeredPane, taskScrollPane);
	}
	
	private void removeTaskButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index = taskListVisual.getSelectedIndex();
		JSONObject toBeRemoved = new JSONObject(taskListVisual.getSelectedValue());
		taskHandler.removeTask(toBeRemoved.getString("uuid"));
		model.removeElementAt(index);
	}
	
	private void editCancelButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switchToTaskPanel(mainLayeredPane, taskScrollPane);
	}
	
	private void editButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index = taskListVisual.getSelectedIndex();
		JSONObject toBeEdited = new JSONObject(taskListVisual.getSelectedValue());
		String uuid = taskHandler.editTask(toBeEdited.getString("uuid"), editTaskNameTextField.getText(), editDatePicker.getText(), editStartTimePicker.getText(), editEndTimePicker.getText(), editCategoryTextField.getText() );
		switchToTaskPanel(mainLayeredPane, taskScrollPane);
		model.removeElementAt(index);
		model.add(index, taskHandler.getTask(uuid).toString());
	}
	
	private void logoButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switchToTaskPanel(mainLayeredPane, taskScrollPane);
	}
	
	private void clearButtonactionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		addTaskNameTextField.setText("");
		addStartTimePicker.clear();
		addEndTimePicker.clear();
		addDatePicker.clear();
		addCategoryTextField.setText("");
	}
}

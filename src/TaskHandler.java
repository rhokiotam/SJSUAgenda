import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

public class TaskHandler {
    private static final String TASK_FILE_NAME = "tasks.txt";

    private JSONArray tasks;

    public TaskHandler() {
        if (!readTaskFile()) tasks = new JSONArray();
    }

    public JSONArray getTasks() {
        return tasks;
    }
    
    public JSONObject getTask(String uuid) {
    	 for (int i = 0; i < tasks.length(); i++) {
             if (tasks.getJSONObject(i).getString("uuid").equals(uuid)) {
            	 return tasks.getJSONObject(i);
             }
         }
    	 return new JSONObject();
    }
    
    public String addTask(String name, String date, String start, String end, String category) {
    	String uuid = UUID.randomUUID().toString();
        JSONObject newTask = new JSONObject("{\"name\":\"" + name + "\",\"date\":\"" + date + "\",\"start\":\"" + start + "\",\"end\":\"" + end + "\",\"category\":\"" + category + "\",\"uuid\":\"" + uuid + "\"}");
        tasks.put(newTask);

        writeTaskFile();
        
        return uuid;
    }

    public String editTask(String UUID, String newName, String newDate, String newStart, String newEnd, String newCategory) {
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getString("uuid").equals(UUID)) {
                tasks.remove(i);
                return addTask(newName, newDate, newStart, newEnd, newCategory);
            }
        }

        writeTaskFile();
        return "";
    }

    public void removeTask(String uuid) {
        for (int i = 0; i < tasks.length(); i++) {
            if (tasks.getJSONObject(i).getString("uuid").equals(uuid)) {
                tasks.remove(i);
            }
        }

        writeTaskFile();
    }

    public boolean readTaskFile() {
        log("reading from task file...");

        try {
            FileReader fileReader = new FileReader(TASK_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            tasks = new JSONArray(bufferedReader.readLine());

            bufferedReader.close();
            return true;
        } catch (Exception e) {
            log("Error reading from task file: " + e.getMessage());
            return false;
        }
    }

    public void writeTaskFile() {
        log("writing to task file...");

        try {
            FileWriter fileWriter = new FileWriter(TASK_FILE_NAME);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(tasks.toString());

            bufferedWriter.close();
        } catch (Exception e) {
            log("Error writing to task file " + e.getMessage());
        }
    }

    public static void log(String message) {
        System.out.println(message);
    }
}

/*
 * $Id: TaskController.java 402 2014-07-28 13:25:15Z lebranch $
 *
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.ui.swing.view;

import java.io.File;

/**
 * The task controller handles all request coming from the {@link TaskView}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 3 juin 2014
 * @see TaskView
 *
 * $LastChangedDate: 2014-07-28 15:25:15 +0200 (lun. 28 juil. 2014) $
 *
 * $LastChangedRevision: 402 $
 */
public class TaskController {

    private TaskView taskView;
    private final File file;

    public TaskController(File file) {
        this.file = file;
        taskView = new TaskView(this);
    }

    /**
     * Return the path of the database file.
     *
     * @return the absolute path
     */
    public String getPathFile() {
        return file.getPath();
    }

    /**
     * Getter on the task view.
     *
     * @return the task view
     */
    public TaskView getTaskView() {
        return taskView;
    }
}

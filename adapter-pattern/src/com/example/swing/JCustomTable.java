package com.example.swing;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
@SuppressWarnings({"serial","unused"})
public class JCustomTable<T> extends JPanel implements ItemListener {

    private JTable table;
    private JComboBox<T> cbTableColumnNames;
    private GenericTableModel<T> model;
    private List<T> data;
    private JLabel info;
    private String columnName;
    private JScrollPane scrollPane;
    private JScrollBar verticalScrollBar;
    private Field columnField;
    private Class<?> valueClass;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public  JCustomTable(Class<? extends T> valueClass) {
        info = new JLabel("Empty!");
        setLayout(new BorderLayout());
        add(info, BorderLayout.SOUTH);
        model = new GenericTableModel(valueClass);
        table = new JTable(model);
        table.setShowHorizontalLines(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        scrollPane = new JScrollPane(table);
        verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.addAdjustmentListener(model);
        verticalScrollBar.setUnitIncrement(1);
        add(scrollPane, BorderLayout.CENTER);
        cbTableColumnNames= new JComboBox(model.getColumnNames().toArray(new String[0]));
        cbTableColumnNames.addItemListener(this);
        add(cbTableColumnNames,BorderLayout.NORTH);
        this.valueClass = valueClass;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        try {
            columnField = valueClass.getDeclaredField(columnName);
            columnField.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        setColumnName(e.getItem().toString());
    }

    @SuppressWarnings({ "rawtypes", "hiding" })
    class GenericTableModel<T> extends AbstractTableModel implements AdjustmentListener {

		private Class<? extends T> cls;
        private List<String> columnNames;
		private List<Class> clsProperties;

        public GenericTableModel(Class<? extends T> cls) {
            this.cls = cls;
            columnNames = new ArrayList<String>();
            clsProperties = new ArrayList<Class>();
            for (Field field : cls.getDeclaredFields()) {
                columnNames.add(field.getName());
                if (field.getType().equals(int.class)) {
                    clsProperties.add(Number.class);
                } else {
                    clsProperties.add(field.getType());
                }
            }
        }

        @Override
        public int getRowCount() {
            return data.size()+(int)((double)scrollPane.getHeight()/table.getRowHeight()+0.5)-1;                        
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return clsProperties.get(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return columnNames.get(column);
        }

        public List<String> getColumnNames() {
            return columnNames;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex>=data.size()) {
                return "";
            }
            Object o = data.get(rowIndex);
            String fieldName = columnNames.get(columnIndex);
            Field f = null;
            try {
                f = o.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(o);
            } catch (Exception ex) {
                return ex.getMessage();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void adjustmentValueChanged(AdjustmentEvent e) {
            try {
                int index = e.getValue() / table.getRowHeight();
				T value = (T) data.get(index);                
                info.setText(columnField.get(value).toString());
            } catch (Exception ex) {
                info.setText("Invalid field");
            }
        }
    }
}

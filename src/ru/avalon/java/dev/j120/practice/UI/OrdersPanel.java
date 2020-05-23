/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import ru.avalon.java.dev.j120.practice.UI.tablemodels.OrderTableModel;
import java.time.LocalDate;
import javax.swing.JTabbedPane;
import javax.swing.table.TableModel;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;
import ru.avalon.java.dev.j120.practice.entity.Person;

public class OrdersPanel extends javax.swing.JPanel {
    private final Mediator mediator;
    private final TableModel otm;
    private JTabbedPane maintab;
    private OrderCardPanel orderCard;
    /**
     * Creates new form OrdersPanel
     * @param mediator
     */
    public OrdersPanel(Mediator mediator) {
        initComponents();
        this.mediator = mediator;
        otm = new OrderTableModel(mediator);
        ordersTable.setModel(otm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        addOrderButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ordersTable = new javax.swing.JTable();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        addOrderButton.setText("Создать заказ");
        addOrderButton.setFocusable(false);
        addOrderButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addOrderButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addOrderButton);

        ordersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ordersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ordersTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ordersTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderButtonActionPerformed
        orderCard = new OrderCardPanel(mediator, this);
        
        maintab = (JTabbedPane) this.getParent();
        maintab.addTab("Новый заказ", orderCard);
        maintab.setSelectedIndex(maintab.getTabCount() -1);
    }//GEN-LAST:event_addOrderButtonActionPerformed

    private void ordersTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordersTableMouseClicked
        if (evt.getButton() == 1 && evt.getClickCount() == 2){
            
            //Получение данных о заказе из таблицы
            Long orderNumber = (Long) otm.getValueAt(ordersTable.getSelectedRow(), 0);
            LocalDate orderDate =(LocalDate) otm.getValueAt(ordersTable.getSelectedRow(), 1);
            Person contactPerson =(Person) otm.getValueAt(ordersTable.getSelectedRow(), 2);
            int discount = (int) otm.getValueAt(ordersTable.getSelectedRow(), 3);
            OrderStatusEnum orderStatus = (OrderStatusEnum) otm.getValueAt(ordersTable.getSelectedRow(), 4);

            //Инициализация панели заказа
            orderCard = new OrderCardPanel(mediator,this, new Order (
                orderNumber, orderDate, contactPerson,
                discount, orderStatus,
                mediator.getOrderList().getOrder(orderNumber).getOrderList()
            ));
            maintab = (JTabbedPane) this.getParent();
            maintab.addTab("Заказ " + orderNumber, orderCard);
            maintab.setSelectedIndex(maintab.getTabCount() -1);
        }
    }//GEN-LAST:event_ordersTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrderButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable ordersTable;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import java.time.LocalDate;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import ru.avalon.java.dev.j120.practice.UI.tablemodels.OrderItemTableModel;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Order;
import ru.avalon.java.dev.j120.practice.entity.OrderStatusEnum;
import ru.avalon.java.dev.j120.practice.entity.Config;
import ru.avalon.java.dev.j120.practice.utils.MyEventListener;
import ru.avalon.java.dev.j120.practice.utils.StateEnum;

public class OrderCardPanel extends javax.swing.JPanel implements MyEventListener {
    private Mediator mediator;    
    private JPanel opParent;     //Панель, из которй открыта текущая вкладка
    private Order order;
    StateEnum state;
    OrderItemTableModel oitm;
    /**Конструктор вкладки нового заказа
     * @param mediator 
     * @param opParent     
     */
    public OrderCardPanel(Mediator mediator, JPanel opParent) {
        initComponents();
        this.mediator = mediator;
        
        this.opParent = opParent;
        //Person person = new Person("Ash Apple", "Mapple st., 5", "+7(961)126-54-98");
        order = new Order(mediator.getOrderList().getFreeNumber(),
                        LocalDate.now(),null, 0, OrderStatusEnum.PREPARING);
        oitm = new OrderItemTableModel(order);
        this.order.addListener(oitm);
        this.order.addListener((MyEventListener) this);
        orderListTable.setModel(oitm); 
        
        orderNumberLabel.setText(String.valueOf( order.getOrderNumber() ));
        orderDateLabel.setText(order.getOrderDate().toString());
        orderStatusComboBox.setSelectedIndex(0);
        orderStatusComboBox.setEnabled(false);        
        discountSpinner.setModel( new SpinnerNumberModel(order.getDiscount(), 0, Config.get().getMaxDiscount(), 1));
        state = StateEnum.NEW;
        mediator.addListener((MyEventListener) this);
    }
    
    /**Конструктор вкладки существующего заказа
     * @param mediator 
     * @param opParent     
     * @param order     
     */
    public OrderCardPanel(Mediator mediator,JPanel opParent, Order order) {
        initComponents();
        this.mediator = mediator;
        
        this.opParent = opParent;
        this.order = order;
        orderNumberLabel.setText( String.valueOf( order.getOrderNumber()) );
        orderDateLabel.setText(order.getOrderDate().toString());
        personLabel.setText(order.getContactPerson().toString());       
                discountSpinner.setModel( new SpinnerNumberModel(order.getDiscount(), 0, Config.get().getMaxDiscount(), 1));
        priceLabel.setText(String.valueOf( order.getTotalPrice().floatValue() ));
        discountPriceLabel.setText(String.valueOf( order.getDiscountPrice().floatValue() ));
        
        switch(order.getOrderStatus()) { 
            case PREPARING: 
                orderStatusComboBox.setSelectedIndex(0);
                break;
            case SHIPPED: 
                orderStatusComboBox.setSelectedIndex(1);
                discountSpinner.setEnabled(false);                
                personButton.setEnabled(false);
                addOrderItemButton.setEnabled(false);
                removeOrderItemButton.setEnabled(false);
                break;
            case CANCELED: 
                orderStatusComboBox.setSelectedIndex(2);
                discountSpinner.setEnabled(false);
                personButton.setEnabled(false);
                addOrderItemButton.setEnabled(false);
                removeOrderItemButton.setEnabled(false);
                break;
        }        
        oitm = new OrderItemTableModel(order);
        this.order.addListener((MyEventListener) oitm);
        this.order.addListener((MyEventListener) this);
        orderListTable.setModel(oitm);
        state = StateEnum.EXIST;
        mediator.addListener((MyEventListener) this);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        discountPriceLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        orderNumberLabel = new javax.swing.JLabel();
        orderDateLabel = new javax.swing.JLabel();
        orderStatusComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        personLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        personButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        discountSpinner = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        priceLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderListTable = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        addOrderItemButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        removeOrderItemButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        submitButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        stateLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(750, 551));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Сумма заказа");

        discountPriceLabel.setText("0");

        jLabel7.setText("Сумма заказа с учетом скидки");

        orderNumberLabel.setText("Номер заказа");

        orderDateLabel.setText("Дата заказа");

        orderStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Готовится", "Отгружен", "Отменен" }));
        orderStatusComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                orderStatusComboBoxItemStateChanged(evt);
            }
        });
        orderStatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderStatusComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Номер заказа");

        jLabel2.setText("Дата заказа");

        personLabel.setBackground(new java.awt.Color(255, 255, 255));
        personLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        personLabel.setPreferredSize(new java.awt.Dimension(4, 20));

        jLabel3.setText("Статус заказа");

        personButton.setText("...");
        personButton.setPreferredSize(new java.awt.Dimension(45, 20));
        personButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Клиент");

        discountSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                discountSpinnerStateChanged(evt);
            }
        });

        jLabel5.setText("Скидка, %");

        priceLabel.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orderNumberLabel)
                            .addComponent(orderDateLabel))
                        .addContainerGap(419, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(priceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(discountPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(discountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(orderStatusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(personLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(personButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(208, 208, 208))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(orderNumberLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(orderDateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(orderStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(personButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(personLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4))
                    .addComponent(discountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(priceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(discountPriceLabel))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        orderListTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(orderListTable);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        addOrderItemButton.setText("Добавить позицию в заказ");
        addOrderItemButton.setFocusable(false);
        addOrderItemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addOrderItemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addOrderItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderItemButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(addOrderItemButton);
        jToolBar1.add(filler1);

        removeOrderItemButton.setText("Удалить позицию");
        removeOrderItemButton.setFocusable(false);
        removeOrderItemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeOrderItemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeOrderItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOrderItemButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(removeOrderItemButton);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        submitButton.setText("Сохранить");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Закрыть");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        stateLabel.setText("Состояние");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 57, Short.MAX_VALUE)
                        .addComponent(submitButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(stateLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(stateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        
        if (order.getContactPerson().getContactPerson() != null && 
            order.getContactPerson().getDeliveryAddress() != null && 
            order.getContactPerson().getPhoneNumber() != null){
                
                mediator.updateOrder(state, order);
        }
        else{
            stateLabel.setText("Укажите контактное лицо");            
            personButton.requestFocusInWindow();
        }
        
    }//GEN-LAST:event_submitButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        JTabbedPane maintab = (JTabbedPane) this.getParent();
        maintab.setSelectedComponent(opParent);
        maintab.remove(this);
        mediator.removeListener((MyEventListener) this);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void addOrderItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderItemButtonActionPerformed
        JTabbedPane maintab = (JTabbedPane) this.getParent();
        maintab.addTab("Добавить в заказ",new GoodsPanel(mediator, this, order, StateEnum.EXIST));
        maintab.setSelectedIndex(maintab.getTabCount() -1);
        cancelButton.setEnabled(false);
    }//GEN-LAST:event_addOrderItemButtonActionPerformed

    private void removeOrderItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOrderItemButtonActionPerformed
        
            Long orderNumber = (Long) oitm.getValueAt(orderListTable.getSelectedRow(), 0);       
            order.removeItem(orderNumber);
    }//GEN-LAST:event_removeOrderItemButtonActionPerformed

    private void personButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personButtonActionPerformed
        JTabbedPane maintab = (JTabbedPane) this.getParent();        
        maintab.addTab("Заказчик",new PersonCardPanel(this, order));        
        maintab.setSelectedIndex(maintab.getTabCount() -1);
        cancelButton.setEnabled(false);
    }//GEN-LAST:event_personButtonActionPerformed

    private void orderStatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderStatusComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orderStatusComboBoxActionPerformed

    private void orderStatusComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_orderStatusComboBoxItemStateChanged
        int selectedIndex = orderStatusComboBox.getSelectedIndex();
        switch (selectedIndex){
            case 0:
                order.setOrderStatus(OrderStatusEnum.PREPARING);
                break;
            case 1:
                order.setOrderStatus(OrderStatusEnum.SHIPPED);
                break;
            case 2:
                order.setOrderStatus(OrderStatusEnum.CANCELED);
                break;
        }
    }//GEN-LAST:event_orderStatusComboBoxItemStateChanged

    private void discountSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_discountSpinnerStateChanged
        order.setDiscount((int) discountSpinner.getModel().getValue());
        discountPriceLabel.setText(String.valueOf( order.getDiscountPrice().floatValue() ));        
    }//GEN-LAST:event_discountSpinnerStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrderItemButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel discountPriceLabel;
    private javax.swing.JSpinner discountSpinner;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel orderDateLabel;
    private javax.swing.JTable orderListTable;
    private javax.swing.JLabel orderNumberLabel;
    private javax.swing.JComboBox<String> orderStatusComboBox;
    private javax.swing.JButton personButton;
    private javax.swing.JLabel personLabel;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JButton removeOrderItemButton;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(String eventType) {
        if (eventType.equals("update")){
            priceLabel.setText(String.valueOf( order.getTotalPrice().floatValue() ));
            discountPriceLabel.setText(String.valueOf( order.getDiscountPrice().floatValue() ));            
        }
        if (eventType.equals("unlock")){
            cancelButton.setEnabled(true);
        }
        if (eventType.equals("personUpdate")){
            personLabel.setText(order.getContactPerson().toString());
        }
        if (eventType.equals("NotEnoughGoods")){
            order.setOrderStatus(OrderStatusEnum.PREPARING);
            stateLabel.setText("Невозможно сохранить заказ. Недостаточно товара на складе.");
        }
        if (eventType.equals("OrderSHIPPED")){
            submitButton.setEnabled(false);
            stateLabel.setText("Статус заказа изменен на: отгружен");
        }
        if (eventType.equals("DataChanged")){
            submitButton.setEnabled(false);
            stateLabel.setText("Заказ сохранён");
        }
        
    }
}
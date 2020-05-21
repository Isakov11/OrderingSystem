/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.avalon.java.dev.j120.practice.UI;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import ru.avalon.java.dev.j120.practice.controller.Mediator;
import ru.avalon.java.dev.j120.practice.entity.Goods;
import ru.avalon.java.dev.j120.practice.utils.StateEnum;


public class GoodsCardPanel extends javax.swing.JPanel {
    private final StateEnum state;
    private final Mediator mediator;    
    private final JPanel opParent;    //Панель, из которй открыта текущая вкладка
    private final long newArticle;
    
    //patterns:
    //1-digit, int and float, only digits
    private final Pattern[] patterns = 
    {Pattern.compile("[\\d.]"), Pattern.compile("\\d+(?:[.]\\d+){0,1}"), Pattern.compile("\\d+"),};
  
    public GoodsCardPanel(Mediator mediator, JPanel opParent) {        
        initComponents();
        this.mediator = mediator;
        this.opParent = opParent;
        newArticle = mediator.getPriceList().getFreeArticle();
        articleLabel.setText(String.valueOf(newArticle));
        state = StateEnum.NEW;
    }
    
    public GoodsCardPanel(Mediator mediator, JPanel opParent, Goods good) {         
        initComponents();        
        this.mediator = mediator;
        this.opParent = opParent;
        newArticle = good.getArticle();
        articleLabel.setText(String.valueOf( newArticle ));
        varietyTextField.setText(good.getVariety() );
        colorTextField.setText( good.getColor() );
        priceTextField.setText(good.getPrice().toString());
        instockTextField.setText(String.valueOf( good.getInstock()) );
        state = StateEnum.EXIST;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        articleLabel = new javax.swing.JLabel();
        varietyTextField = new javax.swing.JTextField();
        colorTextField = new javax.swing.JTextField();
        instockTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        submitButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        stateLabel = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();

        jLabel1.setText("Артикул");

        jLabel2.setText("Наименование");

        jLabel3.setText("Цвет");

        jLabel4.setText("Цена");

        jLabel5.setText("Остаток");

        articleLabel.setText("jLabel6");

        varietyTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varietyTextFieldActionPerformed(evt);
            }
        });

        colorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorTextFieldActionPerformed(evt);
            }
        });

        instockTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                instockTextFieldKeyTyped(evt);
            }
        });

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Close");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        stateLabel.setText("Состояние");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stateLabel)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(submitButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        priceTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                priceTextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(varietyTextField)
                            .addComponent(colorTextField)
                            .addComponent(instockTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(articleLabel)
                                .addGap(0, 182, Short.MAX_VALUE))
                            .addComponent(priceTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(articleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(varietyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(colorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(instockTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void varietyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varietyTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_varietyTextFieldActionPerformed

    private void colorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colorTextFieldActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        
        Matcher matcher;        
        if (varietyTextField.getText().isEmpty()){            
            varietyTextField.requestFocusInWindow();
            stateLabel.setText("Введите наименование товара");
            return;
        }        
        matcher = patterns[1].matcher(priceTextField.getText().trim());
        if (priceTextField.getText().isEmpty() || !matcher.find()){            
            stateLabel.setText("Введите цену товара");
            priceTextField.requestFocusInWindow();
            return;
        }
        matcher = patterns[2].matcher(priceTextField.getText().trim());
        if (instockTextField.getText().isEmpty() || !matcher.find()){            
            stateLabel.setText("Введите количество товара");
            instockTextField.requestFocusInWindow();
            return;
        }
        
        mediator.updateGood(state, new Goods(
                                    newArticle,
                                    varietyTextField.getText(),
                                    colorTextField.getText(),                
                                    new BigDecimal(priceTextField.getText()), 
                                    Long.valueOf(instockTextField.getText() )
                                            )
                            );        
        stateLabel.setText("Изменения сохранены");
        submitButton.setEnabled(false);
    }//GEN-LAST:event_submitButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        JTabbedPane maintab = (JTabbedPane) this.getParent();        
        maintab.setSelectedComponent(opParent);
        maintab.remove(this);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void priceTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceTextFieldKeyTyped
        char[] c = {evt.getKeyChar()};
        String str = new String(c);        
        Matcher matcher = patterns[0].matcher(str);
        if (!matcher.find()){  
            evt.consume();
        }
    }//GEN-LAST:event_priceTextFieldKeyTyped

    private void instockTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_instockTextFieldKeyTyped
        char[] c = {evt.getKeyChar()};
        String str = new String(c);        
        Matcher matcher = patterns[2].matcher(str);
        if (!matcher.find()){  
            evt.consume();
        }
    }//GEN-LAST:event_instockTextFieldKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel articleLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField colorTextField;
    private javax.swing.JTextField instockTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField varietyTextField;
    // End of variables declaration//GEN-END:variables
}

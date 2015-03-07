/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import objects.StackContainer;
import objects.StackTote;

/**
 *
 * @author Kyle
 */
public class ToteStackDialogue extends javax.swing.JFrame
{
    MatchFrame parent = null;

    /**
     * Creates new form ToteStackDialogue
     */
    public ToteStackDialogue(MatchFrame parent)
    {
        this.parent = parent;
        initComponents();
        this.setLocationRelativeTo(null); 
        this.setAlwaysOnTop(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        labelNumTotes = new javax.swing.JLabel();
        fieldNumTotes = new javax.swing.JTextField();
        buttonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tote Stack Information");
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                formKeyTyped(evt);
            }
        });

        labelNumTotes.setText("Number of Totes:");

        fieldNumTotes.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                fieldNumTotesKeyTyped(evt);
            }
        });

        buttonOK.setText("OK");
        buttonOK.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                buttonOKMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelNumTotes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumTotes)
                    .addComponent(fieldNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOK)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonOKMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_buttonOKMouseClicked
    {//GEN-HEADEREND:event_buttonOKMouseClicked
        sendDataAndExit();
    }//GEN-LAST:event_buttonOKMouseClicked

    private void fieldNumTotesKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldNumTotesKeyTyped
    {//GEN-HEADEREND:event_fieldNumTotesKeyTyped
        //is enter
        if (evt.getKeyChar() == 10)
        {
            sendDataAndExit();
        }
    }//GEN-LAST:event_fieldNumTotesKeyTyped

    private void formKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_formKeyTyped
    {//GEN-HEADEREND:event_formKeyTyped
        //is enter
        if (evt.getKeyChar() == 10)
        {
            sendDataAndExit();
        }
    }//GEN-LAST:event_formKeyTyped
    
    
    /**
     * Sends the data entered in the form to the main frame and exits
     */
    private void sendDataAndExit()
    {
        String numTotesString = fieldNumTotes.getText();
        //filter out non-numbers w/ basic regex
        numTotesString = utils.Utils.removeNonNumericChars(numTotesString);
        if (numTotesString != null && !numTotesString.equals(""))
        {            
            parent.addItem(new StackTote(numTotesString));
            this.dispose();
        }
        else
        {
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonOK;
    private javax.swing.JTextField fieldNumTotes;
    private javax.swing.JLabel labelNumTotes;
    // End of variables declaration//GEN-END:variables
}
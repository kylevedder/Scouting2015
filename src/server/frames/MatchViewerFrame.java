/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.frames;

import client.frames.*;
import client.filemanager.ClientFileManager;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import main.Main;
import client.objects.matchdata.CoOpType;
import client.objects.matchdata.HumanPlayerType;
import client.objects.matchdata.MatchData;
import client.objects.matchdata.RobotActivityType;
import client.objects.stacks.StackBase;
import client.objects.stacks.StackContainer;
import client.objects.stacks.StackTote;
import org.json.JSONObject;
import utils.Utils;

/**
 *
 * @author kyle
 */
public class MatchViewerFrame extends javax.swing.JFrame
{

    private static final int SCROLL_SPEED = 16;

    /**
     * Creates new form MatchFrame
     */
    public MatchViewerFrame(MatchData matchData)
    {
        initComponents();
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
            this.setLocationRelativeTo(null);
            this.pack();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(MatchViewerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (matchData != null)
        {
            resetFrame(matchData);
        }
    }

    public void resetFrame(MatchData matchData)
    {
        fieldMatchNum.setText(String.valueOf(matchData.getMatchMatchNumber()));
        fieldMatchNum.setEditable(false);
        fieldScouter.setText(matchData.getMatchScouter());
        fieldScouter.setEditable(false);
        fieldTeamNum.setText(String.valueOf(matchData.getMatchTeamNumber()));
        fieldTeamNum.setEditable(false);
        buttonGroupCoop.clearSelection();
        buttonGroupHumanPlayer.clearSelection();
        buttonGroupRobotActive.clearSelection();
        fieldMatchScore.setText(String.valueOf(matchData.getMatchFinalScore()));
        fieldMatchScore.setEditable(false);
        textAreaRobotActivityComments.setText(matchData.getActivityComment());
        textAreaRobotActivityComments.setEditable(false);
        spinnerNumContainers.setValue(matchData.getAutoNumberContainers());        
        spinnerNumTotes.setValue(matchData.getAutoNumberTotes());        
        checkBoxInAutoZone.setSelected(matchData.isAutoInAutoZone());                
        checkBoxTotesStacked.setSelected(matchData.isAutoTotesStacked());
        checkBoxTotesStacked.setEnabled(true);        
        HumanPlayerType hpType = matchData.getHumanPlayerType();
        switch (hpType)
        {
            case NO_THROW:
                checkBoxHPThrewNoodles.setSelected(false);
                radioHPExcellent.setEnabled(false);
                radioHPMediocre.setEnabled(false);
                radioHPPoor.setEnabled(false);
                
                radioHPExcellent.setSelected(false);
                radioHPMediocre.setSelected(false);
                radioHPPoor.setSelected(false);
                break;

            case POOR:
                checkBoxHPThrewNoodles.setSelected(false);
                radioHPExcellent.setEnabled(true);
                radioHPMediocre.setEnabled(true);
                radioHPPoor.setEnabled(true);
                
                radioHPExcellent.setSelected(false);
                radioHPMediocre.setSelected(false);
                radioHPPoor.setSelected(true);
                break;

            case MEDIOCRE:
                checkBoxHPThrewNoodles.setSelected(false);
                radioHPExcellent.setEnabled(true);
                radioHPMediocre.setEnabled(true);
                radioHPPoor.setEnabled(true);
                
                radioHPExcellent.setSelected(false);
                radioHPMediocre.setSelected(true);
                radioHPPoor.setSelected(false);
                break;

            case EXCELLENT:
                checkBoxHPThrewNoodles.setSelected(false);
                radioHPExcellent.setEnabled(true);
                radioHPMediocre.setEnabled(true);
                radioHPPoor.setEnabled(true);
                
                radioHPExcellent.setSelected(true);
                radioHPMediocre.setSelected(false);
                radioHPPoor.setSelected(false);
                break;

        }

        CoOpType coopType = matchData.getCoopType();
        switch (coopType)
        {
            case NONE:
                radioCoopNone.setSelected(true);
                radioCoopUnstacked.setSelected(false);
                radioCoopStacked.setSelected(false);
                break;
            case STACKED:
                radioCoopNone.setSelected(false);
                radioCoopUnstacked.setSelected(false);
                radioCoopStacked.setSelected(true);
                break;
            case UNSTACKED:
                radioCoopNone.setSelected(false);
                radioCoopUnstacked.setSelected(true);
                radioCoopStacked.setSelected(false);
                break;
        }

        RobotActivityType robotActivityType = matchData.getActivityType();
        switch (robotActivityType)
        {
            case ACTIVE:
                radioActiveNo.setSelected(false);
                radioActivePartial.setSelected(false);
                radioActiveYes.setSelected(true);
                break;
            case INACTIVE:
                radioActiveNo.setSelected(true);
                radioActivePartial.setSelected(false);
                radioActiveYes.setSelected(false);
                break;
            case PARTIALLY:
                radioActiveNo.setSelected(false);
                radioActivePartial.setSelected(true);
                radioActiveYes.setSelected(false);
                break;
        }
        
        

        listContainerStack.setModel(new DefaultListModel<StackBase>());
        for (StackContainer container : matchData.getTeleopContainerStacks())
        {
            this.addItemToList(listContainerStack, container);
        }

        listToteStack.setModel(new DefaultListModel<StackBase>());
        for (StackTote tote : matchData.getTeleopToteStacks())
        {
            this.addItemToList(listToteStack, tote);
        }
    }

    /**
     * Adds an object to the given list
     *
     * @param list
     * @param object
     */
    private void addItemToList(javax.swing.JList<StackBase> list, StackBase object)
    {
        ListModel<StackBase> model = list.getModel();
        DefaultListModel<StackBase> newModel = new DefaultListModel<StackBase>();

        //get all elements
        for (int i = 0; i < model.getSize(); i++)
        {
            newModel.add(i, model.getElementAt(i));
        }
        newModel.addElement(object);
        list.setModel(newModel);
        list.updateUI();
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

        buttonGroupRobotActive = new javax.swing.ButtonGroup();
        buttonGroupCoop = new javax.swing.ButtonGroup();
        buttonGroupHumanPlayer = new javax.swing.ButtonGroup();
        mainPanelScroll = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        panelTeamInfo = new javax.swing.JPanel();
        labelTeamNum = new javax.swing.JLabel();
        fieldTeamNum = new javax.swing.JTextField();
        labelMatchNum = new javax.swing.JLabel();
        fieldMatchNum = new javax.swing.JTextField();
        labelScouter = new javax.swing.JLabel();
        fieldScouter = new javax.swing.JTextField();
        panelRobotActivity = new javax.swing.JPanel();
        panelRobotActivityRadio = new javax.swing.JPanel();
        radioActiveYes = new javax.swing.JRadioButton();
        radioActiveNo = new javax.swing.JRadioButton();
        radioActivePartial = new javax.swing.JRadioButton();
        panelRobotActivityComments = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaRobotActivityComments = new javax.swing.JTextArea();
        panelScoring = new javax.swing.JPanel();
        panelAutonomous = new javax.swing.JPanel();
        panelAutoScoredTotes = new javax.swing.JPanel();
        labelNumTotes = new javax.swing.JLabel();
        spinnerNumTotes = new javax.swing.JSpinner();
        checkBoxTotesStacked = new javax.swing.JCheckBox();
        panelAutoScoredContainer = new javax.swing.JPanel();
        labelNumContainers = new javax.swing.JLabel();
        spinnerNumContainers = new javax.swing.JSpinner();
        panelAutoScoredRobot = new javax.swing.JPanel();
        checkBoxInAutoZone = new javax.swing.JCheckBox();
        panelTeleop = new javax.swing.JPanel();
        panelTeleopToteStacks = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listToteStack = new javax.swing.JList<StackBase>();
        panelTeleopContainerStacks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listContainerStack = new javax.swing.JList<StackBase>();
        panelCoop = new javax.swing.JPanel();
        radioCoopNone = new javax.swing.JRadioButton();
        radioCoopUnstacked = new javax.swing.JRadioButton();
        radioCoopStacked = new javax.swing.JRadioButton();
        panelHumanPlayer = new javax.swing.JPanel();
        radioHPPoor = new javax.swing.JRadioButton();
        radioHPMediocre = new javax.swing.JRadioButton();
        radioHPExcellent = new javax.swing.JRadioButton();
        checkBoxHPThrewNoodles = new javax.swing.JCheckBox();
        panelOutcome = new javax.swing.JPanel();
        labelMatchScore = new javax.swing.JLabel();
        fieldMatchScore = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Match Viewer");
        setResizable(false);

        mainPanelScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        labelTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelTitle.setText("Match Viewer");

        panelTeamInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Team Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        labelTeamNum.setText("Team #:");

        fieldTeamNum.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                fieldTeamNumKeyReleased(evt);
            }
        });

        labelMatchNum.setText("Match #:");

        fieldMatchNum.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                fieldMatchNumKeyReleased(evt);
            }
        });

        labelScouter.setText("Scouter:");

        javax.swing.GroupLayout panelTeamInfoLayout = new javax.swing.GroupLayout(panelTeamInfo);
        panelTeamInfo.setLayout(panelTeamInfoLayout);
        panelTeamInfoLayout.setHorizontalGroup(
            panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeamInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTeamNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldTeamNum, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelMatchNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldMatchNum, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelScouter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldScouter, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTeamInfoLayout.setVerticalGroup(
            panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelScouter)
                .addComponent(fieldScouter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelTeamInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fieldTeamNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(labelTeamNum)
                .addComponent(labelMatchNum)
                .addComponent(fieldMatchNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelRobotActivity.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Robot Activity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        panelRobotActivityRadio.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Active?", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        buttonGroupRobotActive.add(radioActiveYes);
        radioActiveYes.setText("Yes");

        buttonGroupRobotActive.add(radioActiveNo);
        radioActiveNo.setText("No");

        buttonGroupRobotActive.add(radioActivePartial);
        radioActivePartial.setText("Partially");

        javax.swing.GroupLayout panelRobotActivityRadioLayout = new javax.swing.GroupLayout(panelRobotActivityRadio);
        panelRobotActivityRadio.setLayout(panelRobotActivityRadioLayout);
        panelRobotActivityRadioLayout.setHorizontalGroup(
            panelRobotActivityRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRobotActivityRadioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRobotActivityRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioActiveYes)
                    .addComponent(radioActiveNo)
                    .addComponent(radioActivePartial))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        panelRobotActivityRadioLayout.setVerticalGroup(
            panelRobotActivityRadioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRobotActivityRadioLayout.createSequentialGroup()
                .addComponent(radioActiveYes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioActiveNo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioActivePartial))
        );

        panelRobotActivityComments.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Comments (Optional)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        textAreaRobotActivityComments.setColumns(20);
        textAreaRobotActivityComments.setRows(1);
        jScrollPane1.setViewportView(textAreaRobotActivityComments);

        javax.swing.GroupLayout panelRobotActivityCommentsLayout = new javax.swing.GroupLayout(panelRobotActivityComments);
        panelRobotActivityComments.setLayout(panelRobotActivityCommentsLayout);
        panelRobotActivityCommentsLayout.setHorizontalGroup(
            panelRobotActivityCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panelRobotActivityCommentsLayout.setVerticalGroup(
            panelRobotActivityCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        javax.swing.GroupLayout panelRobotActivityLayout = new javax.swing.GroupLayout(panelRobotActivity);
        panelRobotActivity.setLayout(panelRobotActivityLayout);
        panelRobotActivityLayout.setHorizontalGroup(
            panelRobotActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRobotActivityLayout.createSequentialGroup()
                .addComponent(panelRobotActivityRadio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRobotActivityComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRobotActivityLayout.setVerticalGroup(
            panelRobotActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRobotActivityRadio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRobotActivityComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelScoring.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scoring", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        panelAutonomous.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Autonomous", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        panelAutoScoredTotes.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scored Totes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        labelNumTotes.setText("Scored Totes:");

        spinnerNumTotes.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerNumTotesStateChanged(evt);
            }
        });
        spinnerNumTotes.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                spinnerNumTotesPropertyChange(evt);
            }
        });

        checkBoxTotesStacked.setText("Totes Stacked?");
        checkBoxTotesStacked.setEnabled(false);

        javax.swing.GroupLayout panelAutoScoredTotesLayout = new javax.swing.GroupLayout(panelAutoScoredTotes);
        panelAutoScoredTotes.setLayout(panelAutoScoredTotesLayout);
        panelAutoScoredTotesLayout.setHorizontalGroup(
            panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredTotesLayout.createSequentialGroup()
                .addGroup(panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAutoScoredTotesLayout.createSequentialGroup()
                        .addComponent(labelNumTotes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkBoxTotesStacked))
                .addGap(0, 78, Short.MAX_VALUE))
        );
        panelAutoScoredTotesLayout.setVerticalGroup(
            panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredTotesLayout.createSequentialGroup()
                .addGroup(panelAutoScoredTotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumTotes)
                    .addComponent(spinnerNumTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxTotesStacked)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelAutoScoredContainer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scored Containers", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        labelNumContainers.setText("Scored Containers:");

        spinnerNumContainers.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                spinnerNumContainersStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelAutoScoredContainerLayout = new javax.swing.GroupLayout(panelAutoScoredContainer);
        panelAutoScoredContainer.setLayout(panelAutoScoredContainerLayout);
        panelAutoScoredContainerLayout.setHorizontalGroup(
            panelAutoScoredContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredContainerLayout.createSequentialGroup()
                .addComponent(labelNumContainers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerNumContainers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 53, Short.MAX_VALUE))
        );
        panelAutoScoredContainerLayout.setVerticalGroup(
            panelAutoScoredContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredContainerLayout.createSequentialGroup()
                .addGroup(panelAutoScoredContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumContainers)
                    .addComponent(spinnerNumContainers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelAutoScoredRobot.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Scored Robot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        checkBoxInAutoZone.setText("In Auto Zone?");

        javax.swing.GroupLayout panelAutoScoredRobotLayout = new javax.swing.GroupLayout(panelAutoScoredRobot);
        panelAutoScoredRobot.setLayout(panelAutoScoredRobotLayout);
        panelAutoScoredRobotLayout.setHorizontalGroup(
            panelAutoScoredRobotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredRobotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBoxInAutoZone)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAutoScoredRobotLayout.setVerticalGroup(
            panelAutoScoredRobotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutoScoredRobotLayout.createSequentialGroup()
                .addComponent(checkBoxInAutoZone)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelAutonomousLayout = new javax.swing.GroupLayout(panelAutonomous);
        panelAutonomous.setLayout(panelAutonomousLayout);
        panelAutonomousLayout.setHorizontalGroup(
            panelAutonomousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAutonomousLayout.createSequentialGroup()
                .addComponent(panelAutoScoredTotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAutoScoredContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAutoScoredRobot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAutonomousLayout.setVerticalGroup(
            panelAutonomousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAutoScoredTotes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAutoScoredContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelAutoScoredRobot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelTeleop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Teleoperated", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        panelTeleopToteStacks.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tote Stacks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        listToteStack.setDoubleBuffered(true);
        jScrollPane3.setViewportView(listToteStack);

        javax.swing.GroupLayout panelTeleopToteStacksLayout = new javax.swing.GroupLayout(panelTeleopToteStacks);
        panelTeleopToteStacks.setLayout(panelTeleopToteStacksLayout);
        panelTeleopToteStacksLayout.setHorizontalGroup(
            panelTeleopToteStacksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
        );
        panelTeleopToteStacksLayout.setVerticalGroup(
            panelTeleopToteStacksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        panelTeleopContainerStacks.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Container Stacks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jScrollPane2.setViewportView(listContainerStack);

        javax.swing.GroupLayout panelTeleopContainerStacksLayout = new javax.swing.GroupLayout(panelTeleopContainerStacks);
        panelTeleopContainerStacks.setLayout(panelTeleopContainerStacksLayout);
        panelTeleopContainerStacksLayout.setHorizontalGroup(
            panelTeleopContainerStacksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );
        panelTeleopContainerStacksLayout.setVerticalGroup(
            panelTeleopContainerStacksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout panelTeleopLayout = new javax.swing.GroupLayout(panelTeleop);
        panelTeleop.setLayout(panelTeleopLayout);
        panelTeleopLayout.setHorizontalGroup(
            panelTeleopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTeleopLayout.createSequentialGroup()
                .addComponent(panelTeleopToteStacks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeleopContainerStacks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTeleopLayout.setVerticalGroup(
            panelTeleopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTeleopToteStacks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTeleopContainerStacks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelScoringLayout = new javax.swing.GroupLayout(panelScoring);
        panelScoring.setLayout(panelScoringLayout);
        panelScoringLayout.setHorizontalGroup(
            panelScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAutonomous, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTeleop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelScoringLayout.setVerticalGroup(
            panelScoringLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelScoringLayout.createSequentialGroup()
                .addComponent(panelAutonomous, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTeleop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCoop.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Coop", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        buttonGroupCoop.add(radioCoopNone);
        radioCoopNone.setText("None");

        buttonGroupCoop.add(radioCoopUnstacked);
        radioCoopUnstacked.setText("Unstacked");

        buttonGroupCoop.add(radioCoopStacked);
        radioCoopStacked.setText("Stacked");

        javax.swing.GroupLayout panelCoopLayout = new javax.swing.GroupLayout(panelCoop);
        panelCoop.setLayout(panelCoopLayout);
        panelCoopLayout.setHorizontalGroup(
            panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioCoopNone)
                    .addComponent(radioCoopUnstacked)
                    .addComponent(radioCoopStacked))
                .addContainerGap(242, Short.MAX_VALUE))
        );
        panelCoopLayout.setVerticalGroup(
            panelCoopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCoopLayout.createSequentialGroup()
                .addComponent(radioCoopNone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCoopUnstacked)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioCoopStacked))
        );

        panelHumanPlayer.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Human Player Noodle Throwing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        buttonGroupHumanPlayer.add(radioHPPoor);
        radioHPPoor.setSelected(true);
        radioHPPoor.setText("Attempted (0 scored noodles)");
        radioHPPoor.setActionCommand("POOR");

        buttonGroupHumanPlayer.add(radioHPMediocre);
        radioHPMediocre.setText("Mediocre (1 to 2 scored noodles)");
        radioHPMediocre.setToolTipText("");
        radioHPMediocre.setActionCommand("MEDIOCRE");

        buttonGroupHumanPlayer.add(radioHPExcellent);
        radioHPExcellent.setText("Excellent (3+ scored noodles)");
        radioHPExcellent.setActionCommand("Excellent");

        checkBoxHPThrewNoodles.setText("HP Threw Noodles");
        checkBoxHPThrewNoodles.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                checkBoxHPThrewNoodlesStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelHumanPlayerLayout = new javax.swing.GroupLayout(panelHumanPlayer);
        panelHumanPlayer.setLayout(panelHumanPlayerLayout);
        panelHumanPlayerLayout.setHorizontalGroup(
            panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHumanPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkBoxHPThrewNoodles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioHPPoor)
                    .addComponent(radioHPMediocre)
                    .addComponent(radioHPExcellent))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelHumanPlayerLayout.setVerticalGroup(
            panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHumanPlayerLayout.createSequentialGroup()
                .addComponent(radioHPPoor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelHumanPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioHPMediocre)
                    .addComponent(checkBoxHPThrewNoodles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioHPExcellent))
        );

        panelOutcome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Outcome", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        labelMatchScore.setText("Match Total Score:");

        fieldMatchScore.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                fieldMatchScoreActionPerformed(evt);
            }
        });
        fieldMatchScore.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                fieldMatchScoreKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                fieldMatchScoreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                fieldMatchScoreKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout panelOutcomeLayout = new javax.swing.GroupLayout(panelOutcome);
        panelOutcome.setLayout(panelOutcomeLayout);
        panelOutcomeLayout.setHorizontalGroup(
            panelOutcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOutcomeLayout.createSequentialGroup()
                .addComponent(labelMatchScore)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fieldMatchScore, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelOutcomeLayout.setVerticalGroup(
            panelOutcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOutcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(labelMatchScore)
                .addComponent(fieldMatchScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(labelTitle)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelTeamInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelRobotActivity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelScoring, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(panelCoop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelHumanPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(panelOutcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(labelTitle)
                .addGap(23, 23, 23)
                .addComponent(panelTeamInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRobotActivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelScoring, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCoop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelHumanPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOutcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        mainPanelScroll.setViewportView(mainPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanelScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        mainPanelScroll.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void spinnerNumTotesPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_spinnerNumTotesPropertyChange
    {//GEN-HEADEREND:event_spinnerNumTotesPropertyChange

    }//GEN-LAST:event_spinnerNumTotesPropertyChange

    private void spinnerNumTotesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerNumTotesStateChanged
    {//GEN-HEADEREND:event_spinnerNumTotesStateChanged

    }//GEN-LAST:event_spinnerNumTotesStateChanged

    private void spinnerNumContainersStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_spinnerNumContainersStateChanged
    {//GEN-HEADEREND:event_spinnerNumContainersStateChanged

    }//GEN-LAST:event_spinnerNumContainersStateChanged

    private void checkBoxHPThrewNoodlesStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_checkBoxHPThrewNoodlesStateChanged
    {//GEN-HEADEREND:event_checkBoxHPThrewNoodlesStateChanged

    }//GEN-LAST:event_checkBoxHPThrewNoodlesStateChanged

    private void fieldMatchScoreActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fieldMatchScoreActionPerformed
    {//GEN-HEADEREND:event_fieldMatchScoreActionPerformed

    }//GEN-LAST:event_fieldMatchScoreActionPerformed

    private void fieldMatchScoreKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldMatchScoreKeyTyped
    {//GEN-HEADEREND:event_fieldMatchScoreKeyTyped

    }//GEN-LAST:event_fieldMatchScoreKeyTyped

    private void fieldMatchScoreKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldMatchScoreKeyPressed
    {//GEN-HEADEREND:event_fieldMatchScoreKeyPressed

    }//GEN-LAST:event_fieldMatchScoreKeyPressed

    private void fieldMatchScoreKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldMatchScoreKeyReleased
    {//GEN-HEADEREND:event_fieldMatchScoreKeyReleased


    }//GEN-LAST:event_fieldMatchScoreKeyReleased

    private void fieldTeamNumKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldTeamNumKeyReleased
    {//GEN-HEADEREND:event_fieldTeamNumKeyReleased

    }//GEN-LAST:event_fieldTeamNumKeyReleased

    private void fieldMatchNumKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_fieldMatchNumKeyReleased
    {//GEN-HEADEREND:event_fieldMatchNumKeyReleased

    }//GEN-LAST:event_fieldMatchNumKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupCoop;
    private javax.swing.ButtonGroup buttonGroupHumanPlayer;
    private javax.swing.ButtonGroup buttonGroupRobotActive;
    private javax.swing.JCheckBox checkBoxHPThrewNoodles;
    private javax.swing.JCheckBox checkBoxInAutoZone;
    private javax.swing.JCheckBox checkBoxTotesStacked;
    private javax.swing.JTextField fieldMatchNum;
    private javax.swing.JTextField fieldMatchScore;
    private javax.swing.JTextField fieldScouter;
    private javax.swing.JTextField fieldTeamNum;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelMatchNum;
    private javax.swing.JLabel labelMatchScore;
    private javax.swing.JLabel labelNumContainers;
    private javax.swing.JLabel labelNumTotes;
    private javax.swing.JLabel labelScouter;
    private javax.swing.JLabel labelTeamNum;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JList<StackBase> listContainerStack;
    private javax.swing.JList<StackBase> listToteStack;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane mainPanelScroll;
    private javax.swing.JPanel panelAutoScoredContainer;
    private javax.swing.JPanel panelAutoScoredRobot;
    private javax.swing.JPanel panelAutoScoredTotes;
    private javax.swing.JPanel panelAutonomous;
    private javax.swing.JPanel panelCoop;
    private javax.swing.JPanel panelHumanPlayer;
    private javax.swing.JPanel panelOutcome;
    private javax.swing.JPanel panelRobotActivity;
    private javax.swing.JPanel panelRobotActivityComments;
    private javax.swing.JPanel panelRobotActivityRadio;
    private javax.swing.JPanel panelScoring;
    private javax.swing.JPanel panelTeamInfo;
    private javax.swing.JPanel panelTeleop;
    private javax.swing.JPanel panelTeleopContainerStacks;
    private javax.swing.JPanel panelTeleopToteStacks;
    private javax.swing.JRadioButton radioActiveNo;
    private javax.swing.JRadioButton radioActivePartial;
    private javax.swing.JRadioButton radioActiveYes;
    private javax.swing.JRadioButton radioCoopNone;
    private javax.swing.JRadioButton radioCoopStacked;
    private javax.swing.JRadioButton radioCoopUnstacked;
    private javax.swing.JRadioButton radioHPExcellent;
    private javax.swing.JRadioButton radioHPMediocre;
    private javax.swing.JRadioButton radioHPPoor;
    private javax.swing.JSpinner spinnerNumContainers;
    private javax.swing.JSpinner spinnerNumTotes;
    private javax.swing.JTextArea textAreaRobotActivityComments;
    // End of variables declaration//GEN-END:variables

}

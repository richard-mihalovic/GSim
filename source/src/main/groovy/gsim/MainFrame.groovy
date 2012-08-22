package gsim

import javax.swing.WindowConstants as WC
import net.miginfocom.swing.MigLayout

import javax.swing.JFrame

import java.awt.Dimension
import javax.swing.JPanel

import javax.swing.JTextField
import javax.swing.JButton
import javax.swing.BorderFactory
import javax.swing.JSeparator
import javax.swing.JTextArea
import javax.swing.JList
import javax.swing.DefaultListModel
import javax.swing.ListSelectionModel
import java.awt.Container
import java.awt.Component
import javax.swing.JComponent
import groovy.swing.SwingBuilder
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.JFileChooser

import gsim.core.blocks.Block
import javax.swing.JScrollPane
import javax.imageio.ImageIO
import java.awt.Image
import java.awt.Graphics
import org.jfree.chart.ChartPanel
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

import javax.swing.event.ListSelectionListener
import javax.swing.event.ListSelectionEvent

/**
 * @author Richard Mihalovič
 */
class MainFrame implements ActionListener, MouseListener, ListSelectionListener {
	JFrame frame = null
	Schema schema = null

	public static void main(arguments) {
		println System.getProperty("user.dir")

		def mf = new MainFrame()
		mf.frame = new JFrame()

		mf.frame.setDefaultCloseOperation (WC.EXIT_ON_CLOSE)
		mf.frame.setExtendedState(mf.frame.getExtendedState()|JFrame.MAXIMIZED_BOTH)
		//frame.setLocationRelativeTo(null)

		mf.frame.setTitle('GSim schema runner')

		def mainPanel = new JPanel(new MigLayout('insets 0', '[grow]', '[c, grow 100, fill]'))
		def panel = new JPanel(new MigLayout('', '[][grow][]', '[c, grow 100, fill]'))

		mainPanel.setPreferredSize(new Dimension(900, 450))

		panel.add(mf.createLeftPanel(), 'w 200:20%')
		panel.add(mf.createCenterPanel(), 'grow')
		panel.add(mf.createRightPanel(), 'w 300!')

		//mainPanel.add(mf.createTopPanel(), 'grow, wrap')
		mainPanel.add(panel, 'grow')

		mf.frame.add(mainPanel)

		mf.frame.pack();
		mf.frame.setVisible(true)

		//mf.frame = null;
	}

	private def findComponentByName(String name, comp = null) {
		if(comp == null)
			comp = frame.getContentPane()

		if (comp instanceof JComponent) {
			if(comp.getName() == name)
				return comp
		}
		if (comp instanceof Container) {
			Component[] components = ((Container) comp).getComponents();
			def n = components.length
			for (int i = 0; i < n; i++) {
				def foundComponent = findComponentByName(name, components[i]);
				if(foundComponent != null)
					return foundComponent
			}
		}
		return null
	}

	private displaySchemaInfo() {
        def scopesPanel = findComponentByName('scopesPanel')
		scopesPanel.removeAll()
        scopesPanel.repaint()

		schema = new gsim.Schema()

		def parameters = [
		    startTime: 0.0,
			stopTime: 1.0,
			timeStep: 0.05,
			schema: schema
		]

		try {
			groovy.util.Eval.me(
				'simulation',
				parameters,
				findComponentByName('codeEditor').text
			)

			if(schema.blocks.size() > 0) {
				def blocksModel = new DefaultListModel()
				schema.blocks.each {Block block ->
					blocksModel.addElement(block.blockId /*+ ' [' + block.blockName + ']'*/)
				}
				findComponentByName('blocksList').setModel(blocksModel)
			}
		} catch (Exception e) {
			findComponentByName('blocksList').setModel(new DefaultListModel())			
		}
	}

	private def createTopPanel() {
		def panel = new JPanel()

		panel.setLayout(new MigLayout('', '[][grow]'))

		def button = new JButton()
		button.setName('openFileButton')
		button.setText('LOAD SCHEMA: ')
		button.addActionListener(this)

		def textField = new JTextField()
		textField.setName('schemaFileName')
		textField.setEditable(false)

		panel.add(button, 'w 200')
		panel.add(textField, 'growx, wrap')
		panel.add(new JSeparator(), 'span, grow')

		return panel
	}

	private def createLeftPanel() {
		def panel = new JPanel()
		panel.setLayout(new MigLayout('insets 0, gap 0', '[grow]', '[c, grow 37, fill][c, grow 37, fill][c, grow 25, fill]'))

		def panelBlocks = new JPanel()
		panelBlocks.setLayout(new MigLayout('', '[grow]', '[c, grow 100, fill]'))
		panelBlocks.setBorder(BorderFactory.createTitledBorder('BLOCKS: '))

		def panelConnections = new JPanel()
		panelConnections.setLayout(new MigLayout('', '[grow]', '[c, grow 100, fill]'))
		panelConnections.setBorder(BorderFactory.createTitledBorder('CONNECTIONS: '))

		def panelProperties = new JPanel()
		panelProperties.setLayout(new MigLayout('', '[grow]', '[c, grow 100, fill]'))
		panelProperties.setBorder(BorderFactory.createTitledBorder('PROPERTIES: '))

		def model = new DefaultListModel()

		def blocksList = new JList(model)
		blocksList.setName('blocksList')
        blocksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		blocksList.addListSelectionListener(this)
		if(model.size() > 0)
        	blocksList.setSelectedIndex(0);

		panelBlocks.add(new JScrollPane(blocksList), 'grow')

		def propertiesList = new JList(new DefaultListModel())
		propertiesList.setName('propertiesList')
        propertiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		propertiesList.addListSelectionListener(this)

		panelProperties.add(new JScrollPane(propertiesList), 'grow')

		def connectionsList = new JList(new DefaultListModel())
		connectionsList.setName('connectionsList')
        connectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		connectionsList.addListSelectionListener(this)

		panelConnections.add(new JScrollPane(connectionsList), 'grow')
		
		panel.add(panelBlocks, 'grow, wrap')
		panel.add(panelProperties, 'grow, wrap')
		panel.add(panelConnections, 'grow')

		return panel

//        list = new JList(listModel);
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.setSelectedIndex(0);
//        list.addListSelectionListener(this);
//        list.setVisibleRowCount(5);
//        JScrollPane listScrollPane = new JScrollPane(list);
	}

	private def createCenterPanel() {
		def panel = new JPanel()
		panel.setLayout(new MigLayout('', '[grow]', '[][grow 100, fill]'))
		panel.setBorder(BorderFactory.createTitledBorder('EDITOR: '))

		def openButton = new JButton()
		openButton.setName('openButton')
		openButton.setText('Open')
		openButton.addActionListener(this)

		def saveButton = new JButton()
		saveButton.setName('saveButton')
		saveButton.setText('Save')
		saveButton.addActionListener(this)

        def refreshButton = new JButton()
        refreshButton.setName('refreshButton')
        refreshButton.setText('Refresh')
        refreshButton.addActionListener(this)

		def runButton = new JButton()
		runButton.setName('runButton')
		runButton.setText('Run')
		runButton.addActionListener(this)

		def visualizeButton = new JButton()
		visualizeButton.setName('visualizeButton')
		visualizeButton.setText('Visualize')
		visualizeButton.addActionListener(this)

		def panelTop = new JPanel()
		panelTop.setLayout(new MigLayout('insets 0', '[][][]'))

		panelTop.add(openButton, 'right')
		panelTop.add(saveButton, 'right')
		panelTop.add(runButton, 'right')
        panelTop.add(refreshButton, 'right')
		panelTop.add(visualizeButton, 'right')

		def editor = new JTextArea()
		editor.setName('codeEditor')
		editor.setTabSize(4)
//		editor.setFont(new Font('', Font.PLAIN, 14))

		panel.add(panelTop, 'grow, wrap')
		panel.add(new JScrollPane(editor), 'grow')

		return panel
	}

	private def createRightPanel() {
		def panel = new JPanel()

		panel.setName('scopesPanel')
		panel.setLayout(new MigLayout('insets 0', '[grow]'))
		panel.setBorder(BorderFactory.createTitledBorder('SCOPES: '))

		def scrollPanel = new JScrollPane(panel)
		scrollPanel.setName('scrollPanelRight')

//		def button = new JButton()
//		button.setText('...')
//
//		panel.add(button, 'grow')

		return scrollPanel
	}

	def onOpenFileEvent() {
		def fileChooser = new JFileChooser()
		fileChooser.setCurrentDirectory(new File(System.getProperty('user.dir')))
		int returnVal = fileChooser.showOpenDialog()

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile()

			frame.setTitle(file.getAbsolutePath())
			findComponentByName('codeEditor').setText(file.getText('UTF-8'))

			displaySchemaInfo()
        } else {
        }
	}

	def onSaveFileEvent() {
		def schemaFileName = frame.title
		if(schemaFileName.contains('.schema')) {
			new File(schemaFileName).write(
				findComponentByName('codeEditor').text
			)
		} else {
			def fc = new JFileChooser()
			int returnVal = fc.showSaveDialog()
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile()

				frame.setTitle(file.getAbsolutePath())
				file.write(
					findComponentByName('codeEditor').text
				)

				displaySchemaInfo()
			} else {
			}
		}
	}

	def onVisualizeEvent() {
		def tmpDir = System.getProperty('java.io.tmpdir')
		def fileNameBase = tmpDir + '/' + 'schemaVisualization'

		schema = new gsim.Schema()

		def parameters = [
		    startTime: 0.0,
			stopTime: 1.0,
			timeStep: 0.05,
			schema: schema
		]

		groovy.util.Eval.me(
			'simulation',
			parameters,
			findComponentByName('codeEditor').text
		)

		def solver = new gsim.solvers.SolverTargetToSource()

		solver.setSchema(schema)

		new File(fileNameBase + '.dot').write(schema.exportToGraphViz())
		def command = 'dot ' + fileNameBase + '.dot -Tpng -o ' + fileNameBase + '.png'
		println command
		def process = command.execute()
		process.waitFor()
		if(process.exitValue() == 0) {
			new SwingBuilder().frame(defaultCloseOperation: WC.DISPOSE_ON_CLOSE, pack: true, show: true) {
				scrollPane() {
					widget(new ImagePanel(fileNameBase + '.png'))
				}
			}
		}
	}

    private def onRefreshEvent() {
        displaySchemaInfo()
    }

	private def onRunSimulationEvent() {
		schema = new gsim.Schema()

		def parameters = [
		    startTime: 0.0,
			stopTime: 1.0,
			timeStep: 0.05,
			schema: schema
		]

		groovy.util.Eval.me(
			'simulation',
			parameters,
			findComponentByName('codeEditor').text
		)

		def solver = new gsim.solvers.SolverTargetToSource()

		solver.setSchema(schema)

		BigDecimal tStart = parameters.startTime
		BigDecimal tStop = parameters.stopTime
		BigDecimal tStep = parameters.timeStep
		BigDecimal t = 0.0
		Integer i = 0

		t = tStart
		while(t <= tStop) {
			solver.setSimulationParameters(
				new gsim.SimulationParameters(
					t,
					i,
					tStep
				)
			)
			solver.computeSimulationStep()

			t += tStep
			i++
		}

		def targets = solver.findTargetBlocks().groupBy { it.blockId }

		def scopesPanel = findComponentByName('scopesPanel')
		scopesPanel.removeAll()

		targets.each { k,v ->
			if(v.size() != 0) {
				def chartPanel = (ChartPanel)TargetBlockChart.createPanel(v[0], 250, 250)
				chartPanel.setName(k)
				chartPanel.addMouseListener(this)
				
				scopesPanel.add(chartPanel, 'grow,wrap')
			}
		}
		scopesPanel.revalidate()
	}

	void actionPerformed(ActionEvent actionEvent) {
		println actionEvent.source.name
		switch(actionEvent.source.name) {
			case 'openButton':
					onOpenFileEvent()
				break;
			case 'saveButton':
					onSaveFileEvent()
				break;
            case 'refreshButton':
                    onRefreshEvent()
                break;
			case 'runButton':
					onRunSimulationEvent()
				break;
			case 'visualizeButton':
					onVisualizeEvent()
				break;
			default:
				break;
		}
	}

	void mouseClicked(MouseEvent mouseEvent) {
		if(mouseEvent.getButton()) {
			def sourceChartPanel = (ChartPanel)mouseEvent.getComponent()
			def chartPanel = new ChartPanel(sourceChartPanel.getChart())

			new SwingBuilder().frame(pack: true, show: true, defaultCloseOperation: WC.DISPOSE_ON_CLOSE) {
				widget(chartPanel, preferredSize: [300,300])
			}
		}
	}
	
	void mousePressed(MouseEvent mouseEvent) {
	}

	void mouseReleased(MouseEvent mouseEvent) {
	}

	void mouseEntered(MouseEvent mouseEvent) {
	}

	void mouseExited(MouseEvent mouseEvent) {
	}

	void valueChanged(ListSelectionEvent e) {
		JList list = e.getSource()
		if(!e.getValueIsAdjusting()) {
			def model = list.getModel()

			def blockId = list.getSelectedValue()
			def block = schema.findBlock(blockId)

			def parametersModel = new DefaultListModel()
			block.blockParameters.each { key, value ->
				parametersModel.addElement(key + ' = ' + value)
			}
			findComponentByName('propertiesList').setModel(parametersModel)

			def connectionsFromBlock = schema.findConnectionsFromBlock(block)
			def connectionsToBlock = schema.findConnectionsToBlock(block)

			def connectionsModel = new DefaultListModel()
			connectionsFromBlock.each { Connection connection ->
				connectionsModel.addElement(
					'[O] '
					+ connection.getTargetBlock().getBlockId()
					+ ' : '
					+ connection.getTargetValuePath()
				)
			}
			connectionsToBlock.each { Connection connection ->
				connectionsModel.addElement(
					'[I] '
					+ connection.getSourceBlock().getBlockId()
					+ ' : '
					+ connection.getSourceValuePath()
				)
			}
			findComponentByName('connectionsList').setModel(connectionsModel)
		}
	}
}

public class ImagePanel extends JPanel
{
	private String path;
	private Image img;

	public ImagePanel(String path) throws IOException
	{
		this.path = path;
		img = ImageIO.read(new File(path));
		setPreferredSize(
			new Dimension(
				(int)img.getWidth(), (int)img.getHeight()
			)
		)
	}

	public void paint(Graphics g)
	{
		if( img != null)
			g.drawImage(img,0,0, this);
	}
}
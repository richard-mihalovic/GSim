package gsim

import javax.swing.WindowConstants as WC

import groovy.swing.SwingBuilder
import gsim.core.blocks.TargetBlock
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.StandardChartTheme
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.Dimension

/**
 * @author Richard Mihalovič
 */
class TargetBlockChart {
	def TargetBlockChart() {
		ChartFactory.setChartTheme(StandardChartTheme.createDarknessTheme())	
	}

	static def ChartPanel createPanel(TargetBlock block, width=300, height=300) {
		def instance = new TargetBlockChart()
		if(block instanceof gsim.blocks.target.Logger)
			return instance.displayLoggerChart(block, width, height)
		else if(block instanceof gsim.blocks.target.Scope)
			return instance.displayScopeChart(block, width, height)
		else if(block instanceof gsim.blocks.target.XYScope)
			return instance.displayXYScopeChart(block, width, height)
		else
			throw new IllegalArgumentException('Display chart is not supported for ' + block.class)
	}

	def static display(TargetBlock block, width=300, height=300) {
		def instance = new TargetBlockChart()
		def ChartPanel cp = null
		if(block instanceof gsim.blocks.target.Logger)
			cp = instance.displayLoggerChart(block, width, height)
		else if(block instanceof gsim.blocks.target.Scope)
			cp = instance.displayScopeChart(block, width, height)
		else if(block instanceof gsim.blocks.target.XYScope)
			cp = instance.displayXYScopeChart(block, width, height)
		else
			throw new IllegalArgumentException('Display chart is not supported for ' + block.class)

		if(cp != null) {
			def frame = new SwingBuilder().frame(
				title:'Groovy LineChart',
				defaultCloseOperation:WC.EXIT_ON_CLOSE
			) {
				panel(id:'canvas') {
					widget(cp)
				}
			}
			frame.pack()
			frame.show()
		}
	}

	def private displayLoggerChart(gsim.blocks.target.Logger block, width, height) {
		def chartValues = block.getInput(1)

		def data = new XYSeries('MyData')
		chartValues.eachWithIndex { it, i ->
			def Double value = (Double) it.value
			data.add(i, value)
		}

		def chart = ChartFactory.createXYLineChart(
			block.getBlockId(),
			'', // x
			'', // y
			new XYSeriesCollection(data),
			PlotOrientation.VERTICAL,
			false,
			false,
			false
		)

		def cp = new ChartPanel(chart)
		cp.setPreferredSize(new Dimension(width, height))

		return cp
	}

	def private displayScopeChart(gsim.blocks.target.Scope block, width, height) {
		def chartValues = block.getInput(1)

		def data = new XYSeries('MyData')
		chartValues.each { it ->
			def Double x = (Double) it[0].value
			def Double y = (Double) it[1].value
			data.add(x, y)
		}

		def chart = ChartFactory.createXYLineChart(
			block.getBlockId(),
			'', // x
			'', // y
			new XYSeriesCollection(data),
			PlotOrientation.VERTICAL,
			false,
			false,
			false
		)

		def cp = new ChartPanel(chart)
		cp.setPreferredSize(new Dimension(width, height))

		return cp
	}

	def private displayXYScopeChart(gsim.blocks.target.XYScope block, width, height) {
		def chartValues = block.getInput(1)

		def data = new XYSeries('MyData')
		chartValues.each { it ->
			def Double x = (Double) it[0].value
			def Double y = (Double) it[1].value
			data.add(x, y)
		}

		def chart = ChartFactory.createScatterPlot(
			block.getBlockId(),
			'', // x
			'', // y
			new XYSeriesCollection(data),
			PlotOrientation.VERTICAL,
			false,
			false,
			false
		)

		def cp = new ChartPanel(chart)
		cp.setPreferredSize(new Dimension(width, height))

		return cp
	}
}

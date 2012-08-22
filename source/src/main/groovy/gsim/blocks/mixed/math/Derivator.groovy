package gsim.blocks.mixed.math

import gsim.core.blocks.MixedBlock
import gsim.SimulationParameters
import gsim.core.blocks.variables.InputVariable
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.Variable

/**
 *
 * @author Richard Mihalovič
 */
class Derivator extends MixedBlock {
	BigDecimal x0  = null // x(0) 
	BigDecimal y_1 = null // y(n-1)
	BigDecimal y0  = null // y(n)
	BigDecimal y1  = null // y(n+1)

	Derivator() {
		this.setBlockName('Derivator')
	}

	java.util.List<InputVariable> getAvailableInputs() {
		return [
			new InputVariable(
				name: 'in(1)',
				type: VariableType.NUMBER
			)
		]
	}

	java.util.List<OutputVariable> getAvailableOutputs() {
		return [
			new OutputVariable(
				name: 'out(1)',
				type: VariableType.NUMBER
			)
		]
	}

	def getInput(Integer atPosition) {
	}

	def getOutput(Integer atPosition) {
		if(atPosition < 1)
			throw new IllegalArgumentException()
		else {
			return new Variable(
				type: VariableType.NUMBER,
				value: calculatedValue
			)
		}
	}

	def simulationStep(inputs, SimulationParameters parameters) {
		BigDecimal h

		h = parameters.simulationStep

		if(y_1 != null & y0 != null && y1 != null)
			calculatedValue = (y1 - y_1) / (2 * h)
		else
			calculatedValue = null
		
		y_1 = y0
		y0 = y1
		y1 = inputs[1].value

		println 'y-1:' + y_1 + ' y0: ' + y0 + ' y+1:' +y1
	}

	def setParameters(parameters) {
		parameters.each { key, value ->
			// TODO: validate for number
			if(key == 'x0')
				x0 = value
			else if(key == 'y_1')
				y_1 = value
		}
	}

	List getAvailableParameters() {
		return []
	}
}


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
class Integrator extends MixedBlock {
	BigDecimal x0 	= 0.0  // x(0)
	BigDecimal y_1 	= 0.0 // y(n-1)
	BigDecimal y0 	= 0.0  // y(n)

	Integrator() {
		this.setBlockName('Integrator')
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

		// Newton–Cotes: trapezoidal rule
		// integral [a,b] = ((b - a)/2) * (f(a) + f(b)), h = (b-a)
		// integral [a,b] = h/2 * (f(a) + f(b)), f(a) = y_1 , f(b) = y0

		calculatedValue += (h / 2) * (y_1 + y0)

		y0 = inputs[1].value
		y_1 = y0
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


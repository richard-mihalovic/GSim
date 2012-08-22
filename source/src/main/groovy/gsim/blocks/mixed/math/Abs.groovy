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
class Abs extends MixedBlock {
	Abs() {
		this.setBlockName('Abs')
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
//		if(atPosition != 1)
//			throw new IllegalArgumentException()
//		else
//			return calculatedValue
	}

	def getOutput(Integer atPosition) {
		if(atPosition == 1)
			return new Variable(
				type: VariableType.NUMBER,
				value: calculatedValue
			)
		else {
			throw new IllegalArgumentException()
		}
	}

	def simulationStep(inputs, SimulationParameters parameters) {
		calculatedValue = inputs[1].value
		
		if(calculatedValue < 0)
			calculatedValue = -calculatedValue;
	}

	def setParameters(parameters) {
	}

	List getAvailableParameters() {
		return []
	}
}


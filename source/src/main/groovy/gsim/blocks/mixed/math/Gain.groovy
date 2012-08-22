package gsim.blocks.mixed.math

import gsim.core.blocks.MixedBlock
import gsim.SimulationParameters
import gsim.core.blocks.variables.InputVariable
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.BlockParameterType
import gsim.core.blocks.BlockParameter
import gsim.core.blocks.variables.Variable

/**
 *
 * @author Richard Mihalovič
 */
class Gain extends MixedBlock {
	Gain() {
		setBlockName('Gain')
		setParameter('scaleFactor', 1.0)
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
		def scaleFactor = getParameter('scaleFactor')
		calculatedValue = scaleFactor * inputs[1].value
	}

	def setParameters(parameters) {
		parameters.each { key, value ->
			if(key == 'scaleFactor')
				setParameter('scaleFactor', value)
			else
				throw new IllegalArgumentException()
		}
	}

	List getAvailableParameters() {
		return [
		    new BlockParameter(
				name: 'scaleFactor',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			)
		]
	}
}


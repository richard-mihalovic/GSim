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
class Sum extends MixedBlock {
	Sum() {
		setBlockName('Sumator')
		setParameter('operations', '++')
	}

	java.util.List<InputVariable> getAvailableInputs() {
		return [
			new InputVariable(
				name: 'in(1)',
				type: VariableType.NUMBER
			),
			new InputVariable(
				name: 'in(2)',
				type: VariableType.NUMBER
			),
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
		return new Variable(
			type: VariableType.NUMBER,
			value: calculatedValue
		)
	}

	def simulationStep(inputs, SimulationParameters parameters) {
		def operations = getParameter('operations')
		calculatedValue = 0.0

		def n=inputs.size()
		for(def i=1; i<n; i++) {
			if(operations[i-1] == '+')
				calculatedValue += inputs[i].value
			else if(operations[i-1] == '-')
				calculatedValue -= inputs[i].value
		}
	}

	def setParameters(parameters) {
		parameters.each { key, value ->
			if(key == 'operations')
				setParameter('operations', value)
			else
				throw new IllegalArgumentException()
		}
	}

	List getAvailableParameters() {
		return [
		    new BlockParameter(
				name: 'scaleFactor',
				validator: BlockParameterType.Regex(/([\+\-]{2,})/),
				isRequired: false
			)
		]
	}
}


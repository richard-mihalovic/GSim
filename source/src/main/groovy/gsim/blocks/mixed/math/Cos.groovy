package gsim.blocks.mixed.math

import gsim.core.blocks.MixedBlock
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.variables.Variable
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.InputVariable

/**
 *
 * @author Richard Mihalovič
 */
class Cos extends MixedBlock {
	Cos() {
		this.setBlockName('Cos')
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

	def setValue(BigDecimal value) {
		throw new Exception('Operationnot supported!')
	}

	def getInput(Integer atPosition) {
	}

	def getOutput(Integer atPosition) {
		if(atPosition == 1)
			return new Variable(
				type: VariableType.NUMBER,
				value: calculatedValue
			)
		else
			throw new IllegalArgumentException()
	}

    def simulationStep(inputs, gsim.SimulationParameters parameters) {
		calculatedValue = Math.cos(inputs[1].value)
    }

	def setParameters(parameters) {
	}

	List getAvailableParameters() {
		return [
		]
	}
}
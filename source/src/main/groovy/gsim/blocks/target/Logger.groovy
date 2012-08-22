package gsim.blocks.target

import gsim.core.blocks.TargetBlock
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.InputVariable

/**
 *
 * @author Richard Mihalovič
 */
class Logger extends TargetBlock {
	java.util.List<BigDecimal> values = []

	Logger() {
		this.setBlockName('Logger')
	}

	java.util.List<InputVariable> getAvailableInputs() {
		return [
			new InputVariable(
				name: 'in(1)',
				type: VariableType.NUMBER,
				isRequired: true
			)
		]
	}

	/**
	 * Return always last calculatedValue.
	*/
	def getInput(Integer atPosition) {
		if(atPosition == 1)
			return values
		else
			throw new IllegalArgumentException()
	}

	def getOutput(Integer atPosition) {
		throw new Exception('Operation not supported!')
	}

	def addValue(BigDecimal value) {
		this.values.push(value)
	}

    def simulationStep(inputs, gsim.SimulationParameters parameters) {
        addValue(inputs[1])
    }

	def setParameters(parameters) {
	}

	List getAvailableParameters() {
		return []
	}
}
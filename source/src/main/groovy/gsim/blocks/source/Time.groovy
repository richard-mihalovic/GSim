package gsim.blocks.source

import gsim.core.blocks.SourceBlock
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.BlockParameterType
import gsim.core.blocks.BlockParameter
import gsim.core.blocks.variables.Variable

/**
 *
 * @author Richard Mihalovič
 */
class Time extends SourceBlock {
	Time() {
		this.setBlockName('Time')
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
		throw new Exception('Operationnot supported!')
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
		calculatedValue = parameters.simulationTime
    }

	def setParameters(parameters) {
	}

	List getAvailableParameters() {
		return []
	}
}
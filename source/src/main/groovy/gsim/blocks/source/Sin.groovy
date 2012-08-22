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
class Sin extends SourceBlock {
	Sin() {
		this.setBlockName('Sin')
		setParameter('amplitude', 1.0)
		setParameter('frequency', 1.0)
		setParameter('phase', 0.0)
		setParameter('bias', 0.0)
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
		def amplitude = getParameter('amplitude')
		def frequency = getParameter('frequency')
		def phase = getParameter('phase')
		def bias = getParameter('bias')

		calculatedValue = amplitude * Math.sin(frequency * parameters.simulationTime + phase) + bias
    }

	def setParameters(parameters) {
		parameters.each { key, value ->
			if(key == 'amplitude')
				setParameter('amplitude', value)
			else if(key == 'frequency')
				setParameter('frequency', value)
			else if(key == 'phase')
				setParameter('phase', value)
			else if(key == 'bias')
				setParameter('bias', value)
			else
				throw new IllegalArgumentException()
		}
	}

	List getAvailableParameters() {
		return [
		    new BlockParameter(
				name: 'amplitude',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			),
			new BlockParameter(
				name: 'frequency',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			),
			new BlockParameter(
				name: 'phase',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			),
			new BlockParameter(
				name: 'bias',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			)
		]
	}
}
package gsim.blocks.source

import gsim.core.blocks.SourceBlock
import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.BlockParameter
import gsim.core.blocks.BlockParameterType
import gsim.core.blocks.variables.Variable

/**
 *
 * @author Richard Mihalovič
 */
class Step extends SourceBlock {
	Step() {
		this.setBlockName('Step')
		setParameter('tStart', 0.0)
		setParameter('tStop', 1.0)
		setParameter('amplitude', 1.0)
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
		return new Variable(
			type: VariableType.NUMBER,
			value: calculatedValue
		)
	}

    def simulationStep(inputs, gsim.SimulationParameters parameters) {
		def tStart = getParameter('tStart')
		def tStop = getParameter('tStop')
		def amplitude = getParameter('amplitude')

		if(parameters.simulationTime >= tStart && parameters.simulationTime <= tStop)
        	calculatedValue = amplitude
		else
			calculatedValue = 0.0
    }

	def setParameters(parameters) {
		parameters.each { key, value ->
			if(key == 'tStart')
				setParameter('tStart', value)
			else if(key == 'tStop')
				setParameter('tStop', value)
			else if(key == 'amplitude')
				setParameter('amplitude', value)
			else
				throw new IllegalArgumentException()
		}
	}

	List getAvailableParameters() {
		return [
		    new BlockParameter(
				name: 'tStart',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			),
			new BlockParameter(
				name: 'tStop',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			),
			new BlockParameter(
				name: 'amplitude',
				validator: BlockParameterType.Decimal(),
				isRequired: false
			)
		]
	}
}
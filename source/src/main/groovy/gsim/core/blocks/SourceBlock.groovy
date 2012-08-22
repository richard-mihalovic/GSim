package gsim.core.blocks

import gsim.SimulationParameters
import gsim.core.blocks.variables.OutputVariable

/**
 *
 * @author Richard Mihalovič
 */
abstract class SourceBlock extends Block {
    def SourceBlock() {
        this.blockType = BlockType.SOURCE    
    }

	final java.util.List<OutputVariable> getAvailableInputs() {
		return []
	}

	def simulationStep(SimulationParameters parameters) {
	}
}

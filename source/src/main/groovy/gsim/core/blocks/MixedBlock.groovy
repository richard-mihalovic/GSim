package gsim.core.blocks

import gsim.core.blocks.Block

/**
 *
 * @author Richard Mihalovič
 */
abstract class MixedBlock extends Block {
    def MixedBlock() {
        this.blockType = BlockType.MIXED    
    }

	def calculatedValue = 0.0
}


import org.junit.Test
import static org.junit.Assert.*

import gsim.core.blocks.variables.VariableType
import gsim.core.blocks.variables.OutputVariable
import gsim.core.blocks.BlockType

/**
 *
 * @author Richard Mihalovič
 */
class ConstantTest {

	@Test
	void blockDefaultValues() {
		def b = new gsim.blocks.source.Constant()

		assertEquals(1.0, b.getParameter('constant'))
		assertEquals('Constant', b.getBlockName())
        assertEquals(BlockType.SOURCE, b.getBlockType())

		println b.blockParameters
	}

	@Test(expected = Exception.class)
	void setBlockValue() {
		def value = 1234567890.1234567890

		def b = new gsim.blocks.source.Constant()
		b.setValue(value)

//		assertEquals(b.getValue(), calculatedValue)
//		assertEquals(b.getOutput(0), calculatedValue)
//
//		try {
//			b.getInput(0)
//			fail('Constant doesn\'t have input values!');
//		} catch (Exception e) {
//		}
	}

	@Test
	void getOutputVariables() {
		def b = new gsim.blocks.source.Constant()

		def outputs = b.getAvailableOutputs()
		def output = outputs[0]

		assertTrue(outputs.size() > 0)
		assertEquals(OutputVariable.class, output.class)
		assertEquals(VariableType.NUMBER, output.getType())
	}
}
//import org.junit.Test
//import static org.junit.Assert.*
//
//import gsim.blocks.source.Constant
//import gsim.blocks.target.Logger
//import gsim.Connection
//
///**
// *
// * @author richard
// */
//class ConnectionTest {
//	@Test
//	void parsePath() {
//		def sourceBlock = new Constant(value: 1)
//		def targetBlock = new Logger()
//		targetBlock.addValue(7)
//
//		def connection = new Connection()
//		connection.setSourceBlock(sourceBlock)
//		connection.setSourceValuePath('O(1)')
//		connection.setTargetBlock(targetBlock)
//		connection.setTargetValuePath('I(1)')
//
//		assertEquals(connection.getSourceValue(), 1, 0.0)
//		assertEquals(connection.getTargetValue()[0], 7, 0.0)
//		// TODO: check target block calculatedValue
//	}
//}
//

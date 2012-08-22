//import org.junit.Test
//import static org.junit.Assert.*
//
///**
// *
// * @author richard
// */
//class SchemaTest {
//	@Test
//	void checkDefaultSettings() {
//		def s = new gsim.Schema()
//
//		assertEquals(0, s.getBlocks().size())
//		assertEquals(0, s.getConnections().size())
//	}
//
//	@Test
//	void findBlock() {
//		def s = new gsim.Schema()
//		def b1 = new gsim.blocks.source.Constant()
//		def b2 = new gsim.blocks.source.Constant()
//		def b3 = new gsim.blocks.source.Constant()
//
//		b1.blockId = 'b1'
//		b1.setParameter('constant', 1.0)
//
//		b2.blockId = 'b2'
//		b2.setParameter('constant', 2.0)
//
//		b3.blockId = 'b3'
//		b3.setParameter('constant', 3.0)
//
//		s.addBlock(b1)
//		s.addBlock(b2)
//		s.addBlock(b3)
//
//		def bf1 = s.findBlock('b2')
//		assertEquals(b2, bf1)
//
//		def bf2 = s.findBlock('fakeblock')
//		assertEquals(null, bf2)
//	}
//
//	@Test
//	void addConnection() {
//		def schema = new gsim.Schema()
//		def b1 = new gsim.blocks.source.Constant()
//		def b2 = new gsim.blocks.source.Constant()
//
//		b1.blockId = 'b1'
//		b1.setParameter('constant', 1.0)
//
//		b2.blockId = 'b2'
//		b2.setParameter('constant', 2.0)
//
//		schema.addBlock(b1)
//		schema.addBlock(b2)
//
//		schema.addConnection('b1:O1', 'b2:I1')
//
//		def connections = schema.getConnections()
//		def connection = connections[0]
//
//		def sourceBlock = connection.getSourceBlock()
//		def targetBlock = connection.getTargetBlock()
//
//		assertEquals(b1, sourceBlock)
//		assertEquals(b2, targetBlock)
//
//		assertEquals('O1', connection.getSourceValuePath())
//		assertEquals('I1', connection.getTargetValuePath())
//	}
//
//	@Test
//	void validateBlockParameter() {
//		def schema = new gsim.Schema()
//		def b1 = new gsim.blocks.source.Constant()
//		def b2 = new gsim.blocks.source.Constant()
//		def b3 = new gsim.blocks.source.Constant()
//
//		b1.blockId = 'b1'
//		b1.setParameter('constant', 'bad input') // bad
//
//		b2.blockId = 'b2'
//		b2.setParameter('constant', '*1.0') // bad
//
//		b3.blockId = 'b3'
//		b3.setParameter('constant', '1.0') // good
//
//		schema.addBlock(b1)
//		schema.addBlock(b2)
//		schema.addBlock(b3)
//
//		def result = schema.validateBlockParameters()
//
//		assertEquals(2, result.size())
//
//		assertEquals('b1', result[0].blockId)
//		assertEquals(true, result[0].errorMessage.contains('bad parameter value!'))
//
//		assertEquals('b2', result[1].blockId)
//		assertEquals(true, result[1].errorMessage.contains('bad parameter value!'))
//	}
//}
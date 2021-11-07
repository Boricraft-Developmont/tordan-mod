package nl.borisjeletich.tordan.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import nl.borisjeletich.tordan.Tordan;
import nl.borisjeletich.tordan.init.BlockInit;
import nl.borisjeletich.tordan.init.ItemInit;
import nl.borisjeletich.tordan.util.interfaces.IHasModel;

public class CopperRod extends Block implements IHasModel{

	public static final AxisAlignedBB COPPER_ROD = new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
			
			
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL); {
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) {
			facing = EnumFacing.NORTH;
		}
		
		return getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canBlockStay(worldIn, pos) : false;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!this.canBlockStay(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BlockInit.COPPER_ROD));
		}
	}
	
	public CopperRod(String name, Material material) {
			
			super(material);
			setUnlocalizedName(name);
			setRegistryName(name);
			setCreativeTab(Tordan.TORDANMODTAB);
			BlockInit.BLOCKS.add(this);
			ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return COPPER_ROD;
	}
		
	@Override
	public void registerModels() {
			Tordan.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		}
	}



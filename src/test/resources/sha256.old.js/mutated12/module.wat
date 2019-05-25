(module 
	
	;;j << ((3 - i)%4)*8
	
	;;Subtree size 9
	
	(func $Aiyiiimuapr (param  i32) (param  i32) (result i32) 
		
		;;j
		get_local 0
		i32.const 3
		
		;;i
		get_local 1
		i32.sub
		i32.const 4
		i32.rem_s
		i32.const 8
		i32.mul
		i32.shl
		
	)
	(export "Aiyiiimuapr" (func $Aiyiiimuapr))
)
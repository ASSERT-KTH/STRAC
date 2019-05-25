(module 
	
	;;(hash[i]>>(j*8))&255
	
	;;Subtree size 9
	
	(func $Eewajeyiai (param  i32) (param  i32) (result i32) 
		
		;;hash[i]
		get_local 0
		
		;;j
		get_local 1
		i32.const 8
		i32.mul
		i32.shr_u
		i32.const 255
		i32.and
		
	)
	(export "Eewajeyiai" (func $Eewajeyiai))
)
(module 
	
	;;(temp1 + temp2)|0
	
	;;Subtree size 5
	
	(func $Ikeotsiiuil (param  i32) (param  i32) (result i32) 
		
		;;temp1
		get_local 0
		
		;;temp2
		get_local 1
		i32.add
		i32.const 0
		i32.or
		
	)
	(export "Ikeotsiiuil" (func $Ikeotsiiuil))
)
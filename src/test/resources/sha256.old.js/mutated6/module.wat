(module 
	
	;;(asciiBitLength/maxWord)|0
	
	;;Subtree size 5
	
	(func $Svezeeiriui (param  i32) (param  i32) (result i32) 
		
		;;asciiBitLength
		get_local 0
		f32.convert_s/i32
		
		;;maxWord
		get_local 1
		f32.convert_s/i32
		f32.div
		i32.trunc_s/f32
		i32.const 0
		i32.or
		
	)
	(export "Svezeeiriui" (func $Svezeeiriui))
)
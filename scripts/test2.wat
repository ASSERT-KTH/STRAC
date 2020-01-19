(module
  (type $t0 (func (param i32) (result i32)))
  (type $t1 (func (param i32 i32) (result i32)))
  (type $t2 (func (param i32)))
  (type $t3 (func))
  (type $t4 (func (param i32 i32 i32 i32)))
  (type $t5 (func (param i32 i32 i32 i32) (result i32)))
  (type $t6 (func (result i32)))
  (import "env" "malloc" (func $malloc (type $t0)))
  (import "env" "fputc" (func $fputc (type $t1)))
  (import "env" "fgetc" (func $fgetc (type $t0)))
  (import "env" "free" (func $free (type $t2)))
  (import "env" "fopen" (func $fopen (type $t1)))
  (import "env" "fclose" (func $fclose (type $t0)))
  (import "env" "printf" (func $printf (type $t1)))
  (func $__wasm_call_ctors (type $t3))
  (func $b_attach (type $t0) (param $p0 i32) (result i32)
    (local $l0 i32)
    i32.const 12
    call $malloc
    tee_local $l0
    i64.const 0
    i64.store offset=4 align=4
    get_local $l0
    get_local $p0
    i32.store
    get_local $l0)
  (func $b_write (type $t4) (param $p0 i32) (param $p1 i32) (param $p2 i32) (param $p3 i32)
    (local $l0 i32) (local $l1 i32) (local $l2 i32) (local $l3 i32) (local $l4 i32) (local $l5 i32)
    get_local $p3
    i32.load offset=4
    set_local $l0
    block $B0
      block $B1
        get_local $p3
        i32.load offset=8
        tee_local $l1
        i32.const 7
        i32.gt_s
        br_if $B1
        get_local $p1
        i32.eqz
        br_if $B0
      end
      get_local $p2
      i32.const 7
      i32.and
      set_local $l2
      get_local $p0
      get_local $p2
      i32.const 3
      i32.shr_u
      i32.add
      set_local $l3
      loop $L2
        block $B3
          get_local $l1
          i32.const 8
          i32.lt_s
          br_if $B3
          loop $L4
            get_local $l0
            get_local $l1
            i32.const -8
            i32.add
            tee_local $l1
            i32.shr_u
            get_local $p3
            i32.load
            call $fputc
            drop
            get_local $l0
            i32.const -1
            get_local $l1
            i32.shl
            i32.const -1
            i32.xor
            i32.and
            set_local $l0
            get_local $l1
            i32.const 7
            i32.gt_s
            br_if $L4
          end
        end
        block $B5
          get_local $l1
          i32.const 7
          i32.gt_s
          br_if $B5
          get_local $l3
          set_local $p2
          get_local $l2
          set_local $p0
          get_local $p1
          set_local $l4
          get_local $p1
          i32.eqz
          br_if $B5
          loop $L6
            get_local $p2
            i32.const 1
            i32.add
            get_local $p2
            get_local $p0
            i32.const 1
            i32.add
            tee_local $p1
            i32.const 8
            i32.eq
            tee_local $l2
            select
            set_local $l3
            i32.const 0
            get_local $p1
            get_local $l2
            select
            set_local $l2
            i32.const 128
            get_local $p0
            i32.shr_u
            get_local $p2
            i32.load8_u
            i32.and
            i32.const 7
            get_local $p0
            i32.sub
            i32.shr_u
            get_local $l0
            i32.const 1
            i32.shl
            i32.or
            set_local $l0
            get_local $l4
            i32.const -1
            i32.add
            set_local $p1
            get_local $l1
            i32.const 1
            i32.add
            tee_local $l1
            i32.const 7
            i32.gt_s
            br_if $B5
            get_local $l4
            i32.const 1
            i32.ne
            set_local $l5
            get_local $l3
            set_local $p2
            get_local $l2
            set_local $p0
            get_local $p1
            set_local $l4
            get_local $l5
            br_if $L6
          end
        end
        get_local $l1
        i32.const 7
        i32.gt_s
        br_if $L2
        get_local $p1
        br_if $L2
      end
    end
    get_local $p3
    i32.const 8
    i32.add
    get_local $l1
    i32.store
    get_local $p3
    i32.const 4
    i32.add
    get_local $l0
    i32.store)
  (func $b_read (type $t5) (param $p0 i32) (param $p1 i32) (param $p2 i32) (param $p3 i32) (result i32)
    (local $l0 i32) (local $l1 i32) (local $l2 i32) (local $l3 i32)
    get_local $p3
    i32.load offset=8
    set_local $l0
    get_local $p3
    i32.load offset=4
    set_local $l1
    block $B0
      get_local $p1
      i32.eqz
      br_if $B0
      get_local $p2
      i32.const 7
      i32.and
      set_local $l2
      get_local $p0
      get_local $p2
      i32.const 3
      i32.shr_u
      i32.add
      set_local $p2
      loop $L1
        get_local $p1
        i32.const 0
        i32.ne
        set_local $p0
        block $B2
          get_local $p1
          i32.eqz
          br_if $B2
          get_local $l0
          i32.eqz
          br_if $B2
          get_local $l0
          i32.const -1
          i32.add
          set_local $l3
          loop $L3
            get_local $p1
            set_local $l0
            block $B4
              block $B5
                i32.const 1
                get_local $l3
                tee_local $p0
                i32.shl
                get_local $l1
                i32.and
                i32.eqz
                br_if $B5
                get_local $p2
                i32.load8_u
                i32.const 128
                get_local $l2
                i32.shr_u
                i32.or
                set_local $p1
                br $B4
              end
              get_local $p2
              i32.load8_u
              i32.const -129
              get_local $l2
              i32.shr_s
              i32.and
              set_local $p1
            end
            get_local $p2
            get_local $p1
            i32.store8
            get_local $p2
            i32.const 1
            i32.add
            get_local $p2
            get_local $l2
            i32.const 1
            i32.add
            tee_local $l2
            i32.const 7
            i32.gt_u
            tee_local $p1
            select
            set_local $p2
            i32.const 0
            get_local $l2
            get_local $p1
            select
            set_local $l2
            get_local $p0
            i32.const -1
            i32.add
            set_local $l3
            get_local $l0
            i32.const -1
            i32.add
            set_local $p1
            block $B6
              get_local $l0
              i32.const 1
              i32.eq
              br_if $B6
              get_local $p0
              br_if $L3
            end
          end
          get_local $l0
          i32.const 1
          i32.ne
          set_local $p0
          get_local $l3
          i32.const 1
          i32.add
          set_local $l0
        end
        get_local $p0
        i32.eqz
        br_if $B0
        get_local $l0
        i32.const 8
        i32.add
        set_local $l0
        get_local $p3
        i32.load
        call $fgetc
        get_local $l1
        i32.const 8
        i32.shl
        i32.or
        set_local $l1
        get_local $p1
        br_if $L1
      end
    end
    get_local $p3
    i32.const 8
    i32.add
    get_local $l0
    i32.store
    get_local $p3
    i32.const 4
    i32.add
    get_local $l1
    i32.store
    i32.const 0)
  (func $b_detach (type $t2) (param $p0 i32)
    (local $l0 i32)
    block $B0
      get_local $p0
      i32.load offset=8
      tee_local $l0
      i32.eqz
      br_if $B0
      get_local $p0
      get_local $p0
      i32.load offset=4
      i32.const 8
      get_local $l0
      i32.sub
      i32.shl
      tee_local $l0
      i32.store offset=4
      get_local $l0
      get_local $p0
      i32.load
      call $fputc
      drop
    end
    get_local $p0
    call $free)
  (func $__original_main (type $t6) (result i32)
    (local $l0 i32) (local $l1 i32) (local $l2 i32) (local $l3 i32) (local $l4 i32) (local $l5 i32) (local $l6 i32) (local $l7 i32) (local $l8 i32) (local $l9 i32) (local $l10 i32) (local $l11 i32) (local $l12 i32) (local $l13 i32) (local $l14 i32) (local $l15 i32) (local $l16 i32) (local $l17 i32) (local $l18 i32) (local $l19 i32) (local $l20 i32) (local $l21 i32) (local $l22 i32) (local $l23 i32) (local $l24 i32) (local $l25 i32) (local $l26 i32) (local $l27 i32) (local $l28 i32)
    get_global $g0
    i32.const 48
    i32.sub
    tee_local $l0
    set_global $g0
    get_local $l0
    i32.const 32
    i32.add
    i32.const 8
    i32.add
    i32.const 0
    i32.load offset=1032 align=1
    i32.store
    get_local $l0
    i32.const 0
    i64.load offset=1024 align=1
    i64.store offset=32
    get_local $l0
    i32.const 0
    i32.store offset=23 align=1
    get_local $l0
    i64.const 0
    i64.store offset=16
    i32.const 1036
    i32.const 1045
    call $fopen
    set_local $l1
    i32.const 0
    set_local $l2
    i32.const 0
    set_local $l3
    i32.const 0
    set_local $l4
    i32.const 0
    set_local $l5
    loop $L0 (result i32)
      block $B1
        block $B2
          block $B3
            get_local $l5
            br_table $B3 $B2 $B2
          end
          get_local $l0
          i32.const 32
          i32.add
          get_local $l4
          i32.add
          set_local $l6
          i32.const 1
          set_local $l7
          i32.const 7
          set_local $l8
          i32.const 0
          set_local $l5
          br $B1
        end
        get_local $l0
        i32.const 16
        i32.add
        get_local $l25
        i32.add
        set_local $l26
        i32.const 7
        set_local $l27
        i32.const 1
        set_local $l28
        i32.const 1
        set_local $l5
      end
      loop $L4 (result i32)
        block $B5
          block $B6
            block $B7
              block $B8
                block $B9
                  block $B10
                    block $B11
                      get_local $l5
                      br_table $B11 $B10 $B10
                    end
                    block $B12
                      get_local $l2
                      i32.const 8
                      i32.lt_s
                      br_if $B12
                      loop $L13
                        get_local $l3
                        get_local $l2
                        i32.const -8
                        i32.add
                        tee_local $l2
                        i32.shr_u
                        get_local $l1
                        call $fputc
                        drop
                        get_local $l3
                        i32.const -1
                        get_local $l2
                        i32.shl
                        i32.const -1
                        i32.xor
                        i32.and
                        set_local $l3
                        get_local $l2
                        i32.const 7
                        i32.gt_s
                        br_if $L13
                      end
                    end
                    block $B14
                      get_local $l2
                      i32.const 7
                      i32.gt_s
                      br_if $B14
                      get_local $l6
                      set_local $l5
                      get_local $l7
                      set_local $l9
                      get_local $l8
                      set_local $l10
                      get_local $l8
                      i32.eqz
                      br_if $B14
                      loop $L15
                        get_local $l5
                        i32.const 1
                        i32.add
                        get_local $l5
                        get_local $l9
                        i32.const 1
                        i32.add
                        tee_local $l8
                        i32.const 8
                        i32.eq
                        tee_local $l7
                        select
                        set_local $l6
                        i32.const 0
                        get_local $l8
                        get_local $l7
                        select
                        set_local $l7
                        i32.const 128
                        get_local $l9
                        i32.shr_u
                        get_local $l5
                        i32.load8_u
                        i32.and
                        i32.const 7
                        get_local $l9
                        i32.sub
                        i32.shr_u
                        get_local $l3
                        i32.const 1
                        i32.shl
                        i32.or
                        set_local $l3
                        get_local $l10
                        i32.const -1
                        i32.add
                        set_local $l8
                        get_local $l2
                        i32.const 1
                        i32.add
                        tee_local $l2
                        i32.const 7
                        i32.gt_s
                        br_if $B14
                        get_local $l10
                        i32.const 1
                        i32.ne
                        set_local $l11
                        get_local $l6
                        set_local $l5
                        get_local $l7
                        set_local $l9
                        get_local $l8
                        set_local $l10
                        get_local $l11
                        br_if $L15
                      end
                    end
                    get_local $l2
                    i32.const 7
                    i32.gt_s
                    br_if $B7
                    get_local $l8
                    br_if $B6
                    get_local $l4
                    i32.const 1
                    i32.add
                    tee_local $l4
                    i32.const 10
                    i32.ne
                    br_if $B9
                    block $B16
                      get_local $l2
                      i32.eqz
                      br_if $B16
                      get_local $l3
                      i32.const 8
                      get_local $l2
                      i32.sub
                      i32.shl
                      get_local $l1
                      call $fputc
                      drop
                    end
                    get_local $l1
                    call $fclose
                    drop
                    i32.const 1036
                    i32.const 1048
                    call $fopen
                    set_local $l12
                    i32.const 12
                    call $malloc
                    tee_local $l13
                    i64.const 0
                    i64.store offset=4 align=4
                    get_local $l13
                    get_local $l12
                    i32.store
                    i32.const 0
                    set_local $l14
                    i32.const -1
                    set_local $l15
                    i32.const 1
                    set_local $l16
                    i32.const -129
                    set_local $l17
                    i32.const 7
                    set_local $l18
                    i32.const 128
                    set_local $l19
                    i32.const 8
                    set_local $l20
                    get_local $l13
                    i32.const 4
                    i32.add
                    set_local $l21
                    i32.const 10
                    set_local $l22
                    i32.const 0
                    set_local $l23
                    i32.const 0
                    set_local $l24
                    i32.const 0
                    set_local $l25
                    i32.const 1
                    set_local $l5
                    br $L0
                  end
                  get_local $l27
                  get_local $l14
                  i32.ne
                  set_local $l5
                  block $B17
                    get_local $l27
                    i32.eqz
                    br_if $B17
                    get_local $l23
                    i32.eqz
                    br_if $B17
                    get_local $l23
                    get_local $l15
                    i32.add
                    set_local $l10
                    loop $L18
                      get_local $l27
                      set_local $l5
                      block $B19
                        block $B20
                          get_local $l16
                          get_local $l10
                          tee_local $l9
                          i32.shl
                          get_local $l24
                          i32.and
                          i32.eqz
                          br_if $B20
                          get_local $l26
                          i32.load8_u
                          get_local $l19
                          get_local $l28
                          i32.shr_u
                          i32.or
                          set_local $l10
                          br $B19
                        end
                        get_local $l26
                        i32.load8_u
                        get_local $l17
                        get_local $l28
                        i32.shr_s
                        i32.and
                        set_local $l10
                      end
                      get_local $l26
                      get_local $l10
                      i32.store8
                      get_local $l26
                      get_local $l16
                      i32.add
                      get_local $l26
                      get_local $l28
                      get_local $l16
                      i32.add
                      tee_local $l10
                      get_local $l18
                      i32.gt_u
                      tee_local $l28
                      select
                      set_local $l26
                      get_local $l14
                      get_local $l10
                      get_local $l28
                      select
                      set_local $l28
                      get_local $l9
                      get_local $l15
                      i32.add
                      set_local $l10
                      get_local $l5
                      get_local $l15
                      i32.add
                      set_local $l27
                      block $B21
                        get_local $l5
                        get_local $l16
                        i32.eq
                        br_if $B21
                        get_local $l9
                        br_if $L18
                      end
                    end
                    get_local $l5
                    get_local $l16
                    i32.ne
                    set_local $l5
                    get_local $l10
                    get_local $l16
                    i32.add
                    set_local $l23
                  end
                  block $B22
                    get_local $l5
                    i32.eqz
                    br_if $B22
                    get_local $l23
                    get_local $l20
                    i32.add
                    set_local $l23
                    get_local $l13
                    i32.load
                    call $fgetc
                    get_local $l24
                    get_local $l20
                    i32.shl
                    i32.or
                    set_local $l24
                    get_local $l27
                    br_if $B5
                  end
                  get_local $l13
                  get_local $l20
                  i32.add
                  get_local $l23
                  i32.store
                  get_local $l21
                  get_local $l24
                  i32.store
                  get_local $l25
                  get_local $l16
                  i32.add
                  tee_local $l25
                  get_local $l22
                  i32.ne
                  br_if $B8
                  block $B23
                    get_local $l23
                    i32.eqz
                    br_if $B23
                    get_local $l13
                    i32.const 4
                    i32.add
                    get_local $l24
                    i32.const 8
                    get_local $l23
                    i32.sub
                    i32.shl
                    tee_local $l2
                    i32.store
                    get_local $l2
                    get_local $l13
                    i32.load
                    call $fputc
                    drop
                  end
                  get_local $l13
                  call $free
                  get_local $l12
                  call $fclose
                  drop
                  get_local $l0
                  get_local $l0
                  i32.const 16
                  i32.add
                  i32.store
                  i32.const 1051
                  get_local $l0
                  call $printf
                  drop
                  get_local $l0
                  i32.const 48
                  i32.add
                  set_global $g0
                  i32.const 0
                  return
                end
                i32.const 0
                set_local $l5
                br $L0
              end
              i32.const 1
              set_local $l5
              br $L0
            end
            i32.const 0
            set_local $l5
            br $L4
          end
          i32.const 0
          set_local $l5
          br $L4
        end
        i32.const 1
        set_local $l5
        br $L4
      end
    end)
  (func $main (type $t1) (param $p0 i32) (param $p1 i32) (result i32)
    call $__original_main)
  (table $T0 1 1 anyfunc)
  (memory $memory 2)
  (global $g0 (mut i32) (i32.const 66608))
  (global $__heap_base i32 (i32.const 66608))
  (global $__data_end i32 (i32.const 1057))
  (global $__dso_handle i32 (i32.const 1024))
  (export "memory" (memory 0))
  (export "__wasm_call_ctors" (func $__wasm_call_ctors))
  (export "__heap_base" (global 1))
  (export "__data_end" (global 2))
  (export "__dso_handle" (global 3))
  (export "b_attach" (func $b_attach))
  (export "b_write" (func $b_write))
  (export "b_read" (func $b_read))
  (export "b_detach" (func $b_detach))
  (export "main" (func $main))
  (export "__original_main" (func $__original_main))
  (data (i32.const 1024) "abcdefghijk\00test.bin\00wb\00rb\00%10s\0a\00"))

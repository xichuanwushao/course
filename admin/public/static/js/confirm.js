Confirm={
    show: function (text,callback) {
        Swal.fire({
            title: '确认操作?',
            text: text,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '确认!'
        }).then((result) => {
            if (result.isConfirmed) {
                if(callback){
                    callback()
                }
            }
        });

    }
}
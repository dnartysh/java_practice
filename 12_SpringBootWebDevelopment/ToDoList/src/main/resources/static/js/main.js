$(function () {

    const appendTask = function (data) {
        var taskCode = '<a href="#" class="task-link" data-id="' +
            data.id + '">' + data.name + '</a><br>';
        $('#tasks-list')
            .append('<div>' + taskCode + '</div>');
        location.reload();
    };

    //Show adding task form
    $('#show-add-task-form').click(function () {
        $('#task-form').css('display', 'flex');
    });

    // show detail task
    $('.task-link').click(function () {
        $('#detail-task-form').css('display', 'flex');
    });

    //Closing adding task form
    $('#task-form').click(function (event) {
        if (event.target === this) {
            $(this).css('display', 'none');
        }
    });

    // closing detail task info
    $('#detail-task-form').click(function (event) {
        if (event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Adding task
    $('#save-task').click(function () {
        var data = $('#task-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/tasks/',
            data: data,
            success: function (response) {
                $('#task-form').css('display', 'none');
                var task = {};
                task.id = response;
                var dataArray = $('#task-form form').serializeArray();
                for (i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });

    //Getting task
    $(document).on('click', '.task-link', function () {
        var link = $(this);
        var taskId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tasks/' + taskId,
            success: function (response) {
                $('#input-id').val(response.id);
                $('#input-name').val(response.name);
                $('#input-person').val(response.person);
                $('#input-complete').prop('checked', response.complete);
            }
        });

        // Update task
        $('#update-task').click(function () {
            var data = $('#detail-task-form form').serialize();
            $.ajax({
                method: "PUT",
                url: '/tasks/' + taskId,
                data: data,
                success: function (response) {
                    alert("Task update successful!");
                    location.reload();
                }
            });
        });

        // Update all tasks
        $('#update-all-tasks').click(function () {
            var data = $('#detail-task-form form').serialize();
            $.ajax({
                method: "PUT",
                url: '/tasks/',
                data: data,
                success: function (response) {
                    alert("All tasks update successful!");
                    location.reload();
                }
            });
        });
    });

    // Delete all tasks
    $('#delete-all-tasks').click(function () {
        $.ajax({
            method: "DELETE",
            url: '/tasks/',
            success: function (response) {
                alert("Delete all successful!");
                location.reload();
            }
        });
    });
});

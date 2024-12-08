import axios from "axios";

const authItemName = 'acc_token'
const defaultError = (error) => {
    console.error(error)
    console.log('发生了一些错误，请联系管理员')
}
const defaultFailure = (message, status, url) => {
    console.warn(`请求地址: ${url}, 状态码: ${status}, 错误信息: ${message}`)
}
function internalPost(url,data,headers,success,failure,error = defaultError){
    axios.post(url,data,{headers:headers}).then(({data})=>{
        if(data.code === 200){
            success(data.data)
        }else {
            failure(data.message,data.code,url)
        }
    }).catch(err =>{
        error(err)
    })
}
function post(url,data,success,failure = defaultFailure){
    internalPost(url,data,accessHeader(),success,failure)
}
function internalGet(url,headers,success,failure,error = defaultError){
    axios.get(url,{headers:headers}).then(({data})=>{
        if(data.code === 200){
            success(data.data)
        }else {
            failure(data.message,data.code,url)
        }
    }).catch(err =>{
        error(err)
    })
}
function get(url, success, failure = defaultFailure) {
    internalGet(url, accessHeader(), success, failure)
}
function login(username,password,success,failure = defaultFailure){
    internalPost('/api/login',{
        username:username,
        password:password
    },{
        'Content-Type':'application/x-www-form-urlencoded'
    },(data) =>{
        timeAccToken(data.token,data.time)
        console.log('登录成功')
        success(data)
    },failure)
}
//获取token和时效
function timeAccToken(token, time) {
    const accObj = { token:token, expire:time };
    const str = JSON.stringify(accObj);
    localStorage.setItem(authItemName, str);
}
//处理token
const accessHeader = () => {
    const tokenStr = localStorage.getItem(authItemName);
    if (!tokenStr) {
        return {}; // 不传Authorization头部，避免后端返回403
    }

    const tokenObj = JSON.parse(tokenStr);
    return {
        'Authorization': `Bearer ${tokenObj.token}`
    };
};
//获取用户信息
// 获取用户信息的函数
function getUserInfo(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/user/info');
        return;
    }
    get('/api/user/info', (data) => {
        if (data) {
            success(data);
        } else {
            failure('用户信息获取失败', 500, '/api/user/info');
        }
    }, failure);
}
//删除token
function deleteAccessToken() {
    localStorage.removeItem(authItemName);
}
//获取clz
function getClz(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/clz/findAll');
        return;
    }
    get('/api/adm/clz/findAll', (data) => {
        if (data) {
            success(data);
        } else {
            failure('班级信息获取失败', 500, '/api/adm/clz/findAll');
        }
    }, failure);
}
//获取Course
function getCourse(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/clz/findAll');
        return;
    }
    get('/api/adm/course/findAll', (data) => {
        if (data) {
            success(data);
        } else {
            failure('班级信息获取失败', 500, '/api/adm/clz/findAll');
        }
    }, failure);
}
//获取Tea
//获取Tea
function getTea(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/tea/findAll');
        return;
    }
    get('/api/adm/tea/findAll', (data) => {
        if (data) {
            success(data);
        } else {
            failure('教师信息获取失败', 500, '/api/adm/tea/findAll');
        }
    }, failure);
}
//获取stu
function getStu(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/stu/findAll');
        return;
    }
    get('/api/adm/stu/findAll', (data) => {
        if (data) {
            success(data);
        } else {
            failure('学生信息获取失败', 500, '/api/adm/stu/findAll');
        }
    }, failure);
}
//获取task
function getTask(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/task/findAll');
        return;
    }
    get('/api/adm/task/findAll', (data) => {
        if (data) {
            success(data);
        } else {
            failure('任务信息获取失败', 500, '/api/adm/task/findAll');
        }
    }, failure);
}
// 获取stu分页
function getStuPage(pagenum, lines, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/stu/findPage');
        return;
    }

    const form = new FormData();
    form.append("pagenum", pagenum);
    form.append("lines", lines);

    post('/api/adm/stu/findPage', form, success, failure);
}
// 删除clz
function deleteClz(cno, success, failure = defaultFailure) {
    if (!accessHeader['Authorization']) {
        failure('未登录', 403, '/api/adm/clz/del/');
        return;
    }
    const url = `/api/adm/clz/del/?clzno=${cno}`;

    get(url, success, failure);
}
//添加clz
function addClz(clzno, clzname, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/clz/add');
        return;
    }

    const data = new FormData();
    data.append("clzno", clzno);
    data.append("clzname", clzname);

    post('/api/adm/clz/add', data, success, failure);
}
// 更新clz
function updateClz(clzno, clzname, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/clz/update');
        return;
    }

    const data = new FormData();
    data.append("clzno", clzno);
    data.append("clzname", clzname);

    post('/api/adm/clz/update', data, success, failure);
}
//添加course
function addCourse(cno, cname, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/course/add');
        return;
    }

    const data = {
        cno,
        cname
    };

    post('/api/adm/course/add', data, success, failure);
}
// 更新课程的方法
function updateCourse(cno, cname, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/course/update');
        return;
    }
    const data = {
        cno,
        cname
    };

    post('/api/adm/course/update', data, success, failure);
}
//添加stu
function addStu(uname, phone, pwd, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/stu/add');
        return;
    }

    const data = new FormData();
    data.append("uname", uname);
    data.append("phone", phone);
    data.append("pwd", pwd);

    post('/api/adm/stu/add', data, success, failure);
}
//更新stu
function updateStu(uid, uname, phone, pwd, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/stu/update');
        return;
    }

    const data = new FormData();
    data.append("uid", uid);
    data.append("uname", uname);
    data.append("phone", phone);
    data.append("pwd", pwd);

    post('/api/adm/stu/update', data, success, failure);
}
function addTask(user, clz, course, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/task/add');
        return;
    }

    const data = {
        user,
        clz,
        course
    };

    post('/api/adm/task/add', data, success, failure);
}
//task删除
function deleteTask(taskId, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, `/api/adm/task/del/${taskId}`);
        return;
    }

    const url = `/api/adm/task/del/${taskId}`;

    get(url, success, failure);
}
//tea添加
function addTea(uname, phone, pwd, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/tea/add');
        return;
    }

    const data = new FormData();
    data.append("uname", uname);
    data.append("phone", phone);
    data.append("pwd", pwd);

    post('/api/adm/tea/add', data, success, failure);
}
//tea删除
function deleteTea(uid, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/tea/del');
        return;
    }

    const url = `/api/adm/tea/del/?uid=${uid}`;

    get(url, success, failure);
}
function updateTea(uid, uname, phone, role, pic, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/adm/tea/update');
        return;
    }

    const data = new FormData();
    data.append("uid", uid);
    data.append("uname", uname);
    data.append("phone", phone);
    data.append("role", role);
    if (pic) {
        data.append("pic", pic);
    }

    post('/api/adm/tea/update', data, success, failure);
}
//头像获取
function getImage(id, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, `/api/public/showimg/${id}`);
        return;
    }

    const url = `http://127.0.0.1:9000/api/public/showimg/${id}`;

    axios.get(url, { headers })
        .then(response => {
            success(url);
        })
        .catch(error => {
            failure(error.message, error.response ? error.response.status : 500, url);
        });
}
//删除学生
function deleteStu(uid, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, `/api/adm/stu/del?uid=${uid}`);
        return;
    }

    const url = `/api/adm/stu/del?uid=${uid}`;

    get(url, success, failure);
}
//修改密码
// 修改密码
function updatePassword(oldPwd, newPwd, success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/user/updatePwd');
        return;
    }
    const data = {
        oldPwd,
        newPwd
    };
    post('/api/user/updatePwd', data, success, failure);
}
//学生成绩
function getStudentMarks(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/fore/stu/findMyMark');
        return;
    }
    get('/api/fore/stu/findMyMark', (data) => {
        if (data) {
            success(data);
        } else {
            failure('学生成绩获取失败', 500, '/api/fore/stu/findMyMark');
        }
    }, failure);
}
//学生课程
function getStudentCourses(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/fore/stu/findMyCourse');
        return;
    }
    get('/api/fore/stu/findMyCourse', (data) => {
        if (data) {
            success(data);
        } else {
            failure('学生课程获取失败', 500, '/api/fore/stu/findMyCourse');
        }
    }, failure);
}
// 教师查分
function getTeacherMarks(success, failure = defaultFailure) {
    const headers = accessHeader();
    if (!headers['Authorization']) {
        failure('未登录', 403, '/api/fore/tea/findMark');
        return;
    }
    get('/api/fore/tea/findMark', (data) => {
        if (data) {
            success(data);
        } else {
            failure('教师查分失败', 500, '/api/fore/tea/findMark');
        }
    }, failure);
}

export {
    // 用户
    login, // 登录
    getUserInfo, // 获取用户
    deleteAccessToken, //删除token
    updatePassword, // 修改密码

    // 班级
    getClz, // 获取班级
    deleteClz, // 删除班级
    addClz, // 添加新班级
    updateClz, // 更新班级信息

    // 课程管理
    getCourse, // 获取课程
    addCourse, // 添加新课程
    updateCourse, // 更新课程

    // 学生管理
    getStu, // 获取学生列表
    getStuPage, // 分页获取学生
    addStu, // 添加新学生
    updateStu, // 更新学生
    deleteStu, // 删除学生

    // 教师管理
    getTea, // 获取教师列表
    addTea, // 添加新教师
    updateTea, // 更新教师
    deleteTea, // 删除教师

    // 排课管理
    getTask, // 获取
    addTask, // 添加
    deleteTask, // 删除

    // 图片
    getImage, // 获取图片

    //学生
    getStudentMarks, // 学生成绩
    getStudentCourses,//学生课程

    getTeacherMarks,//教师查分
};

import React, {Component} from 'react';

import {Breadcrumb, Button, Col, Divider, Form, Input, message, Row} from 'antd';
import Aux from "../../layouts/Aux";

import * as endpoint from '../../utilities/Endpoints';
import axios from '../../utilities/axios-spring-boot-api-instance'

class AddHdfs extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
    };

    handleSubmit = e => {

        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {

            if (!err) {
                const hide = message.loading('저장중...');
                // console.log('Received values of form: ', values);
                const hdfs = JSON.stringify(values);
                console.log("HDFS to save: ", hdfs);
                axios.post(endpoint.HDFS, values)
                    .then(res => {
                        setTimeout(hide, 0);
                        console.log(res);
                        message.success('성공적으로 파일이 이동되었습니다!', 3);
                    })
                    .catch(err => {
                        console.log(err);
                        setTimeout(hide, 0);
                        message.error('파일 이동 실패!');
                    })
                ;
            }
        });
    };

    render() {

        const {getFieldDecorator} = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: {span: 8},
                sm: {span: 8},
            },
            wrapperCol: {
                xs: {span: 8},
                sm: {span: 8},
            },
        };
        const tailFormItemLayout = {
            labelCol: {span: 8},
            wrapperCol: {span: 8, offset: 8},
        };

        return (
            <Aux>
                <Row>
                    <Col span={21}>
                        <Breadcrumb>
                            <Breadcrumb.Item>하둡 파일 이동</Breadcrumb.Item>
                        </Breadcrumb>
                    </Col>
                    {/*<Col span={3}>

                        <NavLink to={{
                            pathname: "/all-base-rule"
                        }}>
                            <span style={{paddingLeft: '10px'}}>
                                Go to all base rule page
                                <Button
                                    block
                                    icon="unordered-list"
                                    type="primary"
                                >
                                    All Base Rule
                                </Button>
                            </span>
                        </NavLink>
                    </Col>*/}
                </Row>
                <Divider/>
                <Row>
                    <Form {...formItemLayout} onSubmit={this.handleSubmit}>
                        <Form.Item label="목적파일명">
                            {getFieldDecorator('destFileName', {
                                rules: [
                                    {
                                        required: false
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>
                        <Form.Item label="목적경로명">
                            {getFieldDecorator('destPathName', {
                                rules: [
                                    {
                                        required: true,
                                        message: '목적경로명을 입력해주세요!',
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>
                        <Form.Item label="소스파일명">
                            {getFieldDecorator('srcFileName', {
                                rules: [
                                    {
                                        required: false
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>
                        <Form.Item label="소스경로명">
                            {getFieldDecorator('srcPathName', {
                                rules: [
                                    {
                                        required: true,
                                        message: '소스경로명을 입력해주세요!',
                                    },
                                ],
                            })(<Input/>)}
                        </Form.Item>

                        <Form.Item {...tailFormItemLayout}>
                            <Row>
                                <Col span={8}>
                                    <Button type="primary" block htmlType="submit" icon={"edit"}>
                                        저장
                                    </Button>
                                </Col>
                            </Row>
                        </Form.Item>
                    </Form>

                </Row>
            </Aux>
        );
    }
}

const WrappedAddHdfsForm = Form.create({name: 'register'})(AddHdfs);

export default WrappedAddHdfsForm;

import React, {Component} from 'react';

import {Breadcrumb, Button, Col, Divider, Form, message, Row, Spin, Upload, Icon} from 'antd';
import Aux from "../../hoc/Aux/Aux";

import * as endpoint from '../../Endpoints';
import axios from '../../axios-spring-boot-api-instance'


class UploadTemplate extends Component {

    state = {
        confirmDirty: false,
        autoCompleteResult: [],
        nifiRootFlowId: null,
        fileList: [],
        uploading: false,

    };

    componentDidMount() {
        axios.get(endpoint.FLOW_ALL + "/root-flow-id")
            .then(res => {
                console.log(res);
                this.setState({nifiRootFlowId: res.data.data});
            });
    }

    handleUpload = () => {
        const { fileList } = this.state;
        const formData = new FormData();
        fileList.forEach(file => {
            formData.append('template', file);
        });

        this.setState({
            uploading: true,
        });

        // You can use any AJAX library you like

        axios.post(endpoint.PROCESS_GROUP + "/" + this.state.nifiRootFlowId + '/upload/template', formData)
            .then(res => {
                this.setState({
                    fileList: [],
                    uploading: false,
                });
                message.success('Upload NiFi template successfully.');
            })
            .catch(err => {
                this.setState({
                    uploading: false,
                });
                message.error('Upload NiFi template failed.');
            });
    };

    render() {

        const { uploading, fileList } = this.state;
        const props = {
            multiple: true,
            onRemove: file => {
                this.setState(state => {
                    const index = state.fileList.indexOf(file);
                    const newFileList = state.fileList.slice();
                    newFileList.splice(index, 1);
                    return {
                        fileList: newFileList,
                    };
                });
            },
            beforeUpload: file => {
                this.setState(state => ({
                    fileList: [...state.fileList, file],
                }));
                return false;
            },
            fileList,
        };


        if (this.state.nifiRootFlowId){
            return (
                <Aux>
                    <Row>
                        <Col span={21}>
                            <Breadcrumb>
                                <Breadcrumb.Item>Upload NiFi Template</Breadcrumb.Item>
                            </Breadcrumb>
                        </Col>

                    </Row>
                    <Divider/>
                    <Row>

                        <Upload {...props}>
                            <Button>
                                <Icon type="upload" /> Select File
                            </Button>
                        </Upload>
                        <Button
                            type="primary"
                            onClick={this.handleUpload}
                            disabled={fileList.length === 0}
                            loading={uploading}
                            style={{ marginTop: 16 }}
                        >
                            {uploading ? 'Uploading' : 'Start Upload'}
                        </Button>
                    </Row>

                </Aux>
            );
        }

        return <Spin style={{paddingTop: '50%'}} tip="Loading..."> </Spin>;

    }
}

const WrappedUploadTemplateForm = Form.create({name: 'register'})(UploadTemplate);

export default WrappedUploadTemplateForm;

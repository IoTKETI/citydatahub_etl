import React, {Component} from 'react';
import DataTable from 'react-data-table-component';
import {TextField} from "@material-ui/core";

import axios from "../../axios-spring-boot-api-instance";
import * as endpoint from '../../Endpoints';
import {Button, Spin} from "antd";
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from "@material-ui/core/FormControl";
import Input from "@material-ui/core/Input";
import {withStyles} from "@material-ui/core/styles";
import PropTypes from "prop-types";
import NifiFlowDetailSearch from "../NifiInstance/HierarchyView/NifiFlowDetailSearch";
// import RefreshIcon from '@material-ui/icons/Refresh';
// import Fab from '@material-ui/core/Fab';


// import OutlinedInput from "@material-ui/core/OutlinedInput";
// import ReactDOM from "react-dom";


const styles = theme => ({
    rootButton: {
        '& > *': {
            margin: theme.spacing(1),
        },
    },

    root: {
        display: "flex",
        flexWrap: "wrap"
    },
    formControl: {
        margin: theme.spacing.unit,
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing.unit
    },
    formControl1: {
        margin: theme.spacing(1),
        minWidth: 100,
    },
    textField: {
        width: 400,
        marginRight: 10
    },
});
const columns = [
    {
        name: 'Name',
        selector: 'name',
        sortable: true,
        width: '50%'

    },
    {
        name: 'Level',
        selector: 'level',
        sortable: true,
        width: '10%',
        style: {
            backgroundColor: 'rgba(63, 195, 128, 1)',
            color: 'white',
        },
    },
    {
        name: 'URI',
        selector: 'uri',
        sortable: true,
    },
];

class SearchProcessor extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loadingButton: false,
            buttonText: 'Refresh',
            iconLoading: false,

            processGroups: [],
            inputSearchLevel: "",
            inputSearchName: "",
            level: null,
            allLevel: null,
            inputLabelRef: undefined,
            flowDetail: null,
            isSpinDetail: false,

            paginationTotalRows: 0,
            perPage: 10,
            loading: false,
            page: 0,

        };
    }

    handleChangeLevel(level) {

        console.log(level);

        this.setState({
            processGroups: [],
            inputSearchLevel: level,
            loading: true,
            paginationTotalRows: 0
        });

        axios.get(endpoint.SEARCH + "?name=" + this.state.inputSearchName + "&level=" + level + "&page=0&limit=" + this.state.perPage)
            .then(res => {
                console.log(res);
                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        id: component.id,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                        flow: component.processGroupFlow
                    };
                });

                this.setState({
                    processGroups: updatedData,
                    // paginationTotalRows: res.data.data.total_record,
                    paginationTotalRows: res.data.total_record,
                    loading: false
                });

            });
    }

    /*setLevel = (level) => {
        this.setState({level: level});
    };

    setInputLabelRef = (refParam) => {
        this.setState({inputLabelRef: refParam});
    };*/

    handleSearchName = async (nameParam) => {
        console.log(nameParam);

        const name = nameParam.target.value;

        console.log(name);

        this.setState({
            inputSearchName: name,
            processGroups: [],
            loading: true,
            page: 0,
            paginationTotalRows: 0
        });

        const res = await axios.get(endpoint.SEARCH + "?name=" + name + "&level=" + this.state.inputSearchLevel + "&page=0&limit=" + this.state.perPage);

        // .then(res => {
        console.log(res);
        const updatedData = res.data.data.map(component => {
            return {
                key: component.flowId,
                id: component.id,
                flowId: component.flowId,
                name: component.name,
                level: component.level,
                uri: component.uri,
                flow: component.processGroupFlow
            };
        });

        this.setState({
            processGroups: updatedData,
            // paginationTotalRows: res.data.data.total_record,
            paginationTotalRows: res.data.total_record,
            loading: false
        });
        // });
    };

    /*handleSearchLevel = (levelParam) => {
        const level = levelParam.target.value;
        this.setState({
            inputSearchLevel: level
        });
        axios.get(endpoint.SEARCH + "?name=" + this.state.inputSearchName + "&level=" + level)
            .then(res => {
                console.log(res);
                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                    };
                });

                this.setState({processGroups: updatedData});
            });
    };*/

    handleExpandRow = (status, record) => {
        this.setState({flowDetail: null, isSpinDetail: true});
        console.log(status);
        console.log(record);

        axios.get(endpoint.FLOW_BY_ID + record.flowId)
            .then(res => {
                console.log(res);
                this.setState({flowDetail: res.data.data});
                console.log(this.state.flowDetail.flow);
                this.setState({isSpinDetail: false});
            });
    };

    handlePageChange = async page => {
        console.log("handlePageChange");

        if (page > 0)
            page--;
        else page = 0;

        const perPage = this.state.perPage;

        this.setState({loading: true});

        const response = await axios.get(
            endpoint.SEARCH + "?name=" + this.state.inputSearchName
            + "&level=" + this.state.inputSearchLevel
            + "&page=" + page
            + "&limit=" + perPage
        );

        const updatedData = response.data.data.map(component => {
            return {
                key: component.flowId,
                id: component.id,
                flowId: component.flowId,
                name: component.name,
                level: component.level,
                uri: component.uri,
                flow: component.processGroupFlow
            };
        });

        this.setState({
            loading: false,
            processGroups: updatedData,

            paginationTotalRows: response.data.total_record,
            page: page,
        });
    };

    handlePerRowsChange = async (perPage, page) => {
        console.log("handlePerRowsChange");

        if (page > 0)
            page--;
        else page = 0;

        this.setState({loading: true});

        const response = await axios.get(
            endpoint.SEARCH + "?name=" + this.state.inputSearchName
            + "&level=" + this.state.inputSearchLevel
            + "&page=" + page
            + "&limit=" + perPage
        );

        const updatedData = response.data.data.map(component => {
            return {
                key: component.flowId,
                id: component.id,
                flowId: component.flowId,
                name: component.name,
                level: component.level,
                uri: component.uri,
                flow: component.processGroupFlow
            };
        });

        this.setState({
            loading: false,
            processGroups: updatedData,
            perPage: perPage,

            paginationTotalRows: response.data.total_record,
            page: page
        });
    };

    enterLoading = () => {

        this.setState({
            processGroups: [],
            loading: true,
            page: 0,
            paginationTotalRows: 0,
            loadingButton: true,
            buttonText: 'Refreshing...'
        });

        axios.get(endpoint.SEARCH + "?name=" + this.state.inputSearchName + "&level=" + this.state.inputSearchLevel + "&page=0&limit=" + this.state.perPage)
            .then(res => {
                console.log(res);
                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        id: component.id,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                        flow: component.processGroupFlow
                    };
                });

                this.setState({
                    processGroups: updatedData,
                    paginationTotalRows: res.data.total_record,
                    loading: false,
                    loadingButton: false,
                    buttonText: 'Refresh'
                });
            });
    };

    componentDidMount() {
        axios.get(endpoint.SEARCH)
            .then(res => {
                console.log(res);
                const updatedData = res.data.data.map(component => {
                    return {
                        key: component.flowId,
                        flowId: component.flowId,
                        name: component.name,
                        level: component.level,
                        uri: component.uri,
                        id: component.id,
                        flow: component.processGroupFlow
                    };
                });

                console.log("Process Groups: ", updatedData);

                this.setState({processGroups: updatedData, paginationTotalRows: res.data.total_record});
            });

        axios.get(endpoint.ALL_LEVEL)
            .then(res => {
                console.log(res);
                let i = 1;
                const levels = res.data.data.map(level => {
                    i++;
                    return (
                        <MenuItem key={i} value={level}>{level}</MenuItem>
                    )
                });
                this.setState({allLevel: levels});
            });
    }

    render() {
        const {classes} = this.props;
        /*let detailFlow = null;
        if (this.state.flowDetail && this.state.isSpinDetail === false) {
            detailFlow = <NifiFlowDetail spin={this.state.isSpinDetail} flow={this.state.flowDetail}/>;
        }*/
        /*const labelOffsetWidth = this.state.inputLabelRef
            ? ReactDOM.findDOMNode(this.state.inputLabelRef).offsetWidth
            : 0;*/

        let show = <Spin style={{paddingLeft: '50%'}} tip={"Loading..."}/>;

        const data = this.state.processGroups;

        if (data && this.state.allLevel)
            return (
                <div>
                    <DataTable
                        title="NiFi Process Groups List"
                        columns={columns}
                        data={data}
                        defaultSortField="title"
                        selectableRows={false}
                        selectableRowsHighlight={true}
                        expandableRows={true}
                        expandOnRowClicked={true}
                        // onRowExpandToggled={(status, record) => this.handleExpandRow(status, record)}
                        expandableRowsComponent={<NifiFlowDetailSearch spin={false}
                                                                 flow={data}/>}
                        progressPending={this.state.loading}
                        progressComponent={<Spin tip={"Loading..."}/>}
                        pagination={true}
                        paginationTotalRows={this.state.paginationTotalRows}
                        paginationPerPage={10}
                        paginationServer={true}

                        onChangeRowsPerPage={this.handlePerRowsChange}
                        onChangePage={this.handlePageChange}

                        paginationResetDefaultPage={true}
                        paginationDefaultPage={this.state.page + 1}

                        highlightOnHover={true}
                        striped={true}
                        pointerOnHover={true}
                        noTableHead={false}
                        persistTableHead={false}
                        noHeader={false}
                        subHeader={true}
                        subHeaderComponent={
                            (
                                <div style={{display: 'flex', alignItems: 'center'}}>

                                    {/*<FormControl variant="outlined" className={classes.formControl1}>
                                        <InputLabel
                                            ref={this.setInputLabelRef}
                                            htmlFor="outlined-level-simple"
                                        >
                                            Level
                                        </InputLabel>
                                        <Select
                                            value={this.state.level}
                                            onChange={e => {
                                                this.setLevel(e.target.value)
                                            }}
                                            input={
                                                <OutlinedInput
                                                    labelWidth={labelOffsetWidth}
                                                    name="level"
                                                    id="outlined-level-simple"
                                                />
                                            }
                                        >
                                            <MenuItem value="">
                                                <em>None</em>
                                            </MenuItem>
                                            <MenuItem value={0}>0</MenuItem>
                                            <MenuItem value={1}>1</MenuItem>
                                            <MenuItem value={2}>2</MenuItem>
                                            {this.state.allLevel}
                                        </Select>
                                    </FormControl>*/}

                                    <FormControl className={classes.formControl}>
                                        <InputLabel htmlFor="level">Level</InputLabel>
                                        <Select
                                            name="level"
                                            value={this.state.level}
                                            onChange={event => this.handleChangeLevel(event.target.value)}
                                            input={<Input id="level"/>}
                                        >
                                            <MenuItem key={1} value=""><em>None</em></MenuItem>

                                            {this.state.allLevel}

                                        </Select>
                                    </FormControl>

                                    {/*<TextField
                                        className={classes.textField}
                                        value={this.state.inputSearchLevel}
                                        onChange={level => this.handleSearchLevel(level)}
                                        id="outlined-basic"
                                        label="Search Level..."
                                        variant="outlined" size="small" style={{marginRight: '15px'}}/>*/}

                                    <TextField
                                        className={classes.textField}
                                        value={this.state.inputSearchName}
                                        onChange={name => this.handleSearchName(name)}
                                        id="outlined-basic" label="Search Name..."
                                        variant="outlined" size="small"/>

                                    {/*<div className={classes.rootButton}>
                                        <Fab color="primary" aria-label="add">
                                            <RefreshIcon/>
                                        </Fab>
                                    </div>*/}

                                    <Button type="primary" loading={this.state.loadingButton}
                                            onClick={this.enterLoading}>
                                        {this.state.buttonText}
                                    </Button>

                                </div>
                            )
                        }
                        subHeaderAlign={"left"}
                        fixedHeader={false}
                        fixedHeaderScrollHeight="300px"
                    />
                </div>

            );
        else return show;
    }
}

SearchProcessor
    .propTypes = {
    classes: PropTypes.object.isRequired
};
export default withStyles(styles)(SearchProcessor);
